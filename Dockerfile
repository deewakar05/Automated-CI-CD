# 1. Base Image - Using official Node 20 alpine version for light-weight container footprint
FROM node:20-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Define default Environment variables
ENV PORT=3000
ENV NODE_ENV=production

# 4. Copy package metadata files first to leverage Docker layer caching
# This ensures npm packages are not re-downloaded unless package.json/package-lock.json changes
COPY package*.json ./

# 5. Clean install only production dependencies (skips devDependencies like nodemon, jest, supertest)
RUN npm ci --omit=dev

# 6. Copy the rest of the application source code
COPY . .

# 7. Document the port the container intends to run on at runtime
EXPOSE 3000

# 8. Start the application using node runtime directly
CMD ["node", "src/server.js"]