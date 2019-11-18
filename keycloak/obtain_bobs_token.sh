curl -X POST \
  http://localhost:8088/auth/realms/quotesc/protocol/openid-connect/token \
  -H 'Authorization: Basic ZnJvbnRlbmQtc2VydmljZTo4M2EwMzg2MS04NWUxLTQxN2ItYmY1Yi03ZjZlZTI4ZTZkYmQ=' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'username=bob&password=bob&grant_type=password&undefined=' | jq ".access_token"