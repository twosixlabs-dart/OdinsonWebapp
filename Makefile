IMAGE_PREFIX = reynoldsm88
IMAGE_NAME = odinson-webapp
IMG := $(IMAGE_PREFIX)/$(IMAGE_NAME)

ifndef CI_COMMIT_REF_NAME
	APP_VERSION := "latest"
else ifeq ("$(CI_COMMIT_REF_NAME)", "master")
	APP_VERSION := "latest"
else
	APP_VERSION := $(shell cat version.sbt | cut -d\" -f2 | cut -d '-' -f1)
endif

docker-build:
	sbt "project webapp" "dist"
	docker build -t $(IMG):$(APP_VERSION) .

docker-push: docker-build
	docker push $(IMG):$(APP_VERSION)
