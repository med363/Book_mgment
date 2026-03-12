#!/bin/bash
cd client || exit
echo "Installing dependencies..."
npm install
npm install axios react-router-dom lucide-react react-hook-form clsx tailwind-merge react-toastify framer-motion date-fns
npm install -D tailwindcss postcss autoprefixer

echo "Initializing Tailwind..."
npx tailwindcss init -p

echo "Setup complete."
