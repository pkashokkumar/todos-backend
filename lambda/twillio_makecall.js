var https = require('https');
var queryString = require('querystring');

// Lambda function:
exports.handler = function (event, context) {
  console.log('Running event');

  // Tells Twilio to make a voice call to the number provided in the event data.
  // End the lambda function when the send function completes.
  MakeCall(event.message, function (status) { context.done(null, status); });
};

// Triggers a voice call using the Twilio API
// completedCallback(status) : Callback with status message when the function completes.
function MakeCall(message, completedCallback) {

  // Options and headers for the HTTP request
  var options = {
    host: 'api.twilio.com',
    port: 443,
    path: '/2010-04-01/Accounts/' + process.env.TWILIO_ACCOUNT_SID + '/Calls.json',
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + new Buffer(process.env.TWILIO_ACCOUNT_SID + ':' + process.env.TWILIO_AUTH_TOKEN).toString('base64')
    }
  };

  // Setup the HTTP request and our response
  var req = https.request(options, function (res) {
    res.setEncoding('utf-8');
    // Collect response data as it comes back.
    var responseString = '';
    res.on('data', function (data) {
      responseString += data;
    });

    // Log the responce received from Twilio.
    // Or could use JSON.parse(responseString) here to get at individual properties.
    res.on('end', function () {
      console.log('Twilio Response: ' + responseString);
      completedCallback('API request sent successfully.');
    });
  });

  // Handler for HTTP request errors.
  req.on('error', function (e) {
    console.error('HTTP error: ' + e.message);
    completedCallback('API request completed with error(s).');
  });

  // Use twimlets to generate our TwiML on the fly.
  // Twilio expects a URL it can POST at, so we can't host the TwiML on S3 :(
  // This says "ThisData Alarm", then plays a great wee barbershop song by the PagerDuty team
  //    source: https://support.pagerduty.com/hc/en-us/articles/219534828)
  const twiml = {
    Twiml:  '<Response>\n' +
              '<Say voice="alice">This is a todo reminder to ' + message + '</Say>\n' +
            '</Response>'
  };
  
  const url = "http://twimlets.com/echo?" + queryString.stringify(twiml);

  // Create the payload we want to send, including the Twiml location, from
  //   which Twilio will fetch instructions when the call connects
  var body = {
    To: process.env.TWILIO_TO_NUMBER,
    From: process.env.TWILIO_FROM_NUMBER,
    Url: url,
  };
  var bodyString = queryString.stringify(body);

  // Send the HTTP request to the Twilio API.
  // Log the message we are sending to Twilio.
  req.write(bodyString);
  req.end();
}
