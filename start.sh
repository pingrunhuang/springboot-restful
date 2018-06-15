#/bin/bash

docker run --name test -v $pwd:/home -p 8081:8081 -it my-spring-boot /bin/bash
