#!/bin/bash
# VM HTTPS Setup Script using Certbot and Nginx

# Step 1: Install Certbot and Nginx on your VM
sudo apt update
sudo apt install -y nginx certbot python3-certbot-nginx

# Step 2: Configure Nginx for your application
cat > /etc/nginx/sites-available/task-app << 'EOF'
server {
    listen 80;
    server_name vm.rajinitasksapi-devop.me;

    # Frontend
    location / {
        proxy_pass http://localhost:3000;  # Adjust port if different
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # Backend API
    location /api/ {
        proxy_pass http://localhost:8080;  # Adjust port if different
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
EOF

# Step 3: Enable the site
sudo ln -s /etc/nginx/sites-available/task-app /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx

# Step 4: Get SSL certificate from Let's Encrypt
sudo certbot --nginx -d vm.rajinitasksapi-devop.me

# Step 5: Test automatic renewal
sudo certbot renew --dry-run

echo "✅ HTTPS setup complete!"
echo "🌐 Access your VM deployment at: https://vm.rajinitasksapi-devop.me"