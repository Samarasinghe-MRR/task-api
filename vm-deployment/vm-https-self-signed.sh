#!/bin/bash
# Alternative: HTTPS on different port (443 or 8443)

# Create self-signed certificate for IP access
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/ssl/private/vm-selfsigned.key \
    -out /etc/ssl/certs/vm-selfsigned.crt \
    -subj "/CN=34.100.195.80"

# Configure Nginx for HTTPS on port 443
cat > /etc/nginx/sites-available/task-app-ssl << 'EOF'
server {
    listen 443 ssl;
    server_name 34.100.195.80;

    ssl_certificate /etc/ssl/certs/vm-selfsigned.crt;
    ssl_certificate_key /etc/ssl/private/vm-selfsigned.key;

    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;

    # Frontend
    location / {
        proxy_pass http://localhost:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto https;
    }

    # Backend API  
    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto https;
    }
}

# Keep HTTP on port 80 as well
server {
    listen 80;
    server_name 34.100.195.80;

    # Redirect to HTTPS
    return 301 https://$server_name$request_uri;
}
EOF

sudo ln -s /etc/nginx/sites-available/task-app-ssl /etc/nginx/sites-enabled/
sudo nginx -t && sudo systemctl reload nginx

echo "✅ Self-signed HTTPS setup complete!"
echo "🌐 Access via: https://34.100.195.80/"
echo "⚠️ Note: Browser will show security warning due to self-signed certificate"