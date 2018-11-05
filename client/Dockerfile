FROM node:8.12.0-alpine

ENV APP_DIR=/usr/src/app

RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR

ADD package.json package.json
RUN yarn

ADD . .
RUN yarn build

EXPOSE 3000
CMD yarn start -H 0.0.0.0
