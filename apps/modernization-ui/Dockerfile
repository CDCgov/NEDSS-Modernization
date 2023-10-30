FROM node:18 as ui-builder

WORKDIR /usr/modernization-ui

COPY package.json package.json

RUN npm install

# Copy required sources
COPY . .

RUN npm run build

FROM nginx:latest

COPY --from=ui-builder /usr/modernization-ui/build /usr/share/nginx/html/
