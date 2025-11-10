# VM HTTPS Deployment Setup

This directory contains scripts and configurations for setting up HTTPS on the VM deployment.

## Current VM Deployment
- **HTTP URL**: http://34.100.195.80/
- **Target**: Enable HTTPS without affecting GKE deployment

## GKE Deployment (Unchanged)
- **HTTPS URL**: https://rajinitasksapi-devop.me
- **Certificate**: Google Managed Certificate
- **Status**: ✅ Working

## VM HTTPS Options

### Option 1: Subdomain with Let's Encrypt (Recommended)
**Target URL**: `https://vm.rajinitasksapi-devop.me`

#### Prerequisites
1. Add DNS A record in Namecheap: `vm.rajinitasksapi-devop.me` → `34.100.195.80`
2. Ensure VM has Nginx and Certbot available

#### Setup
```bash
# Copy script to VM and run
scp vm-https-setup.sh user@34.100.195.80:~/
ssh user@34.100.195.80
chmod +x vm-https-setup.sh
sudo ./vm-https-setup.sh
```

### Option 2: Self-Signed Certificate
**Target URL**: `https://34.100.195.80/`

#### Setup
```bash
# Copy script to VM and run
scp vm-https-self-signed.sh user@34.100.195.80:~/
ssh user@34.100.195.80
chmod +x vm-https-self-signed.sh
sudo ./vm-https-self-signed.sh
```

**Note**: Browser will show security warning due to self-signed certificate.

### Option 3: GCP Load Balancer
Uses separate Google Cloud Load Balancer with managed certificate.

#### Prerequisites
1. Reserve separate static IP
2. Create DNS record for subdomain
3. Apply Kubernetes resources

#### Setup
```bash
# Reserve IP
gcloud compute addresses create vm-app-ip --global

# Apply resources
kubectl apply -f vm-gcp-loadbalancer.yaml
```

## Repository Organization

### Backend Repository (task-api)
```
task-api/
├── vm-deployment/          # ← VM HTTPS setup files
│   ├── vm-https-setup.sh
│   ├── vm-https-self-signed.sh
│   ├── vm-gcp-loadbalancer.yaml
│   └── README.md
├── k8s/                    # ← GKE deployment files
└── src/                    # ← Application source
```

### Frontend Repository (task-app-frontend)
```
task-app-frontend/
├── .github/workflows/      # ← Frontend CI/CD
├── k8s/                    # ← Frontend K8s configs
└── src/                    # ← React application
```

## Deployment URLs After Setup

| Environment | URL | Certificate | Status |
|-------------|-----|-------------|---------|
| GKE | https://rajinitasksapi-devop.me | Google Managed | ✅ Active |
| VM (Option 1) | https://vm.rajinitasksapi-devop.me | Let's Encrypt | 🔧 Setup needed |
| VM (Option 2) | https://34.100.195.80/ | Self-signed | 🔧 Setup needed |

## Next Steps

1. Choose your preferred option
2. Follow the setup instructions above
3. Update any documentation with new URLs
4. Test both deployments independently

## Notes
- VM HTTPS setup is independent of GKE deployment
- No changes needed to existing GKE configuration
- Both environments can run simultaneously