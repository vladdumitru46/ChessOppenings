# Use an official Nginx image to serve static assets
FROM nginx:alpine

# Copy static assets to Nginx server
COPY . /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
