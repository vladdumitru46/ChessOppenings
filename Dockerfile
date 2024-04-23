FROM nginx:latest

COPY . . 
WORKDIR /frontend
EXPOSE 3000:3000