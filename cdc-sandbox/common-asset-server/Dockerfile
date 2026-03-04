FROM nginx:1.29.4-alpine

RUN mkdir /usr/share/nginx/css; \
    mkdir /usr/share/nginx/images; \
    mkdir /usr/share/nginx/js

COPY nginx/nginx.conf /etc/nginx/nginx.conf

COPY html/* /usr/share/nginx/html
COPY css/* /usr/share/nginx/css
COPY images/* /usr/share/nginx/images
COPY js/*    /usr/share/nginx/js