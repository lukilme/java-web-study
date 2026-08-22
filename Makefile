.PHONY: help build clean run-main up down logs restart dev

help: ## Exibe este menu de ajuda
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'

build: ## Compila e gera os artefatos (WAR e JAR)
	mvn clean package

clean: ## Limpa as pastas target
	mvn clean

run-main: ## Compila e executa o Main.java do servlet localmente
	mvn compile exec:java -pl servlet-app -Dexec.mainClass="com.example.servlet.Main"

up: ## Sobe os containers do Docker
	docker compose up -d

down: ## Para os containers do Docker
	docker compose down

logs: ## Visualiza os logs dos containers em tempo real
	docker compose logs -f

restart: ## Reinicia os containers do Docker
	docker compose restart

dev: build up ## Recompila o projeto e sobe os containers
