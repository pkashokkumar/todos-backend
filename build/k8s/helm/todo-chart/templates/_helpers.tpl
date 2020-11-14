{{/* dbsecret configmap entries */}}
{{- define "dbsecret.entries" }}
  dbuser: {{ .Values.dbuser }}
  dbpassword: {{ .Values.dbpassword }}
{{- end }}
