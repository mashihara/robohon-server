./mvnw package
$(aws ecr get-login --no-include-email --region ap-northeast-1)
docker-compose build
docker tag robohon:0.0.3 039230025712.dkr.ecr.ap-northeast-1.amazonaws.com/robohon:0.0.3
docker push 039230025712.dkr.ecr.ap-northeast-1.amazonaws.com/robohon:0.0.3
