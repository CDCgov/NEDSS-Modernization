FROM node:22 as ui-builder

WORKDIR /usr/modernization-ui

COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm install

# Copy required sources
COPY . .

RUN npm run build

FROM nginx:latest

COPY --from=ui-builder /usr/modernization-ui/build /usr/share/nginx/html/
