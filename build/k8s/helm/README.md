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

# Upload the index.yaml and chart.tgz to S3
# Static host from there
# Added a CNAME record in the namecheap dns
# Now this repo can be added to helm

helm repo add cloudease http://helm.cloudease.xyz

# Search for the chart
helm search repo todo

# Further work
# Need to keep db in a separate chart and make app dependent on it
