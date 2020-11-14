# Create new helm chart
helm create todo-chart

# Replace all the k8s yamls into tempaltes sub folde
# Need to change to use values.yaml

# Dry run
helm install --dry-run --debug todo ./todo-chart

# Run
helm install -debug todo ./todo-chart

# Package chart
helm package ./todo-chart

# Create Helm repo index
helm repo index .

# Upload the index.yaml and chart.tgz to the S3 bucket
# helm.techdreams.site

# Static host from there
# When replacing the index.yaml and tgz files, make them individually public in S3
# Added a CNAME record in the netlify dns
# Now this repo can be added to helm

helm repo add techdreams http://helm.techdreams.site

# Search for the chart
helm search repo todo

# Install the chart
helm install todo techdreams/todo-service

# List the installed app
helm list

# Uninstall the app
helm uninstall todo

# List added helm repos
helm repo list

# Remove a repo
helm repo remove techdreams
