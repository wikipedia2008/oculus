#!/bin/sh

host='kmalawski@108.59.81.83'
knife solo prepare $host
knife solo cook $host nodes/master.json

# slaves
host='kmalawski@23.236.57.222'
knife solo prepare $host
knife solo cook $host nodes/master.json

host='kmalawski@108.59.86.163'
knife solo prepare $host
knife solo cook $host nodes/master.json

host='kmalawski@162.222.176.34'
knife solo prepare $host
knife solo cook $host nodes/master.json
