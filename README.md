# enaio-microservice-example
Yet another enaio microservice

# Contract Service

Un microservizio Spring Boot per la gestione dei contratti e processi BPM.

## 📋 Panoramica

Il Contract Service è un'applicazione Java basata su Spring Boot che gestisce:
- Contratti e relativi stati
- Processi BPM (Business Process Management)
- Scheduler per attività automatiche
- Integrazione con servizi DMS (Document Management System)
- Notifiche e promemoria per scadenze contrattuali

## 🛠️ Stack Tecnologico

- **Java**: 21
- **Spring Boot**: 3.4.0
- **Spring Cloud**: 2024.0.0
- **Maven**: 3.9+
- **Log4j2**: 2.24.1
- **OpenAPI**: 3.x (SpringDoc)

## 📂 Struttura del Progetto

```
contract-service/
├── src/main/java/com/os/services/contract/
│   ├── controller/          # REST Controllers
│   │   └── SchedulerController.java
│   ├── service/            # Business Logic
│   │   ├── BpmService.java
│   │   ├── DmsService.java
│   │   └── impl/
│   ├── dao/                # Data Access Objects
│   │   ├── ApiClient.java
│   │   ├── BpmDao.java
│   │   └── ResultDao.java
│   ├── model/              # Domain Models
│   │   ├── ContractData.java
│   │   ├── DmsObject.java
│   │   ├── ProcessModel.java
│   │   └── FilterByIndexData.java
│   ├── web/error/          # Error Handling
│   │   └── RestExceptionHandler.java
│   └── ContractServiceApplication.java
├── src/main/resources/
│   ├── application.yml
│   └── bootstrap.yml
├── config/                 # Configurazioni ambiente
├── pom.xml
└── README.md
```

## 🚀 Avvio Rapido

### Prerequisiti
- Java 21+
- Maven 3.9+
- Accesso ai servizi esterni (BPM, DMS)

### Installazione e Avvio

1. **Clona il repository:**
   ```bash
   git clone <repository-url>
   cd contract-service
   ```

2. **Compila il progetto:**
   ```bash
   mvn clean compile
   ```

3. **Crea il JAR:**
   ```bash
   mvn package
   ```

4. **Avvia l'applicazione:**
   ```bash
   java -jar target/contractservice.jar
   ```

### Configurazione

Il servizio utilizza Spring Cloud Config. Assicurati che:
- Il Config Server sia attivo
- Le configurazioni siano presenti in `config/`
- Eureka Server sia disponibile per service discovery

## 🔧 Configurazione

### Profili Disponibili

- **default**: Configurazione base
- **develop**: Configurazione per sviluppo (include config locali)

### Variabili di Ambiente Principali

```bash
# Server Configuration
SERVER_PORT=8080

# Spring Cloud Config
SPRING_CLOUD_CONFIG_URI=http://config-server:8888

# Eureka Service Discovery
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-server:8761/eureka

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_OS_SERVICES_CONTRACT=DEBUG
```

## 📊 Monitoraggio e Osservabilità

### Actuator Endpoints

- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Jolokia JMX**: `/actuator/jolokia`

### API Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔄 Funzionalità Principali

### Scheduler Controller
- **Attività automatiche** per gestione contratti
- **Controllo stati** di firma e elaborazione
- **Notifiche** per scadenze contrattuali
- **Integrazione BPM** per avvio processi

### Gestione Contratti
- **CRUD operations** per dati contrattuali
- **Filtri avanzati** per ricerca
- **Stati del contratto**: Active, Expired, Pending, etc.
- **Tipi di contratto**: Recurring, One-time, etc.

### Integrazione BPM
- **Avvio processi** automatici
- **Gestione modelli** di processo
- **Monitoraggio** stato processi

## 🏗️ Build e Deploy

### Build Locale
```bash
# Build completo
mvn clean package

# Solo compilazione
mvn compile

# Verifica dipendenze
mvn dependency:tree
```

### Docker (Opzionale)
```dockerfile
FROM openjdk:21-jre-slim
COPY target/contractservice.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Script di Utilità

Il progetto include script batch per Windows:
- `build-production.bat`: Build completo per produzione
- `remove-all-tests.bat`: Pulizia file di test

## 🔒 Sicurezza

- **Spring Security**: Integrazione base
- **HTTPS**: Configurabile via properties
- **CORS**: Configurazione per domini autorizzati

## 📝 Log e Debug

### Configurazione Logging
```yaml
logging:
  level:
    com.os.services.contract: DEBUG
    org.springframework.cloud: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
```

### Log Files
- Applicazione: `logs/contract-service.log`
- Errori: `logs/contract-service-error.log`

## 🐛 Troubleshooting

### Problemi Comuni

1. **Errore di connessione Config Server:**
   - Verifica che il Config Server sia attivo
   - Controlla l'URL in `bootstrap.yml`

2. **Errore Eureka Registration:**
   - Verifica configurazione Eureka Client
   - Controlla network connectivity

3. **Errore BPM Integration:**
   - Verifica credenziali e endpoint BPM
   - Controlla log per dettagli errori

### Comandi Debug
```bash
# Verifica health
curl http://localhost:8080/actuator/health

# Verifica configurazione
curl http://localhost:8080/actuator/env

# Verifica metriche
curl http://localhost:8080/actuator/metrics
```

## 🤝 Contribuire

1. Fork del repository
2. Crea feature branch (`git checkout -b feature/nuova-funzionalita`)
3. Commit delle modifiche (`git commit -am 'Aggiunge nuova funzionalità'`)
4. Push del branch (`git push origin feature/nuova-funzionalita`)
5. Crea Pull Request

## 📄 Licenza

Questo progetto è distribuito sotto licenza [MIT License](LICENSE).

## 📞 Supporto

Per supporto tecnico o domande:
- **Issues**: Utilizza GitHub Issues
- **Email**: support@os-services.com
- **Documentazione**: Consulta la Wiki del progetto

---

## 📈 Versioni

- **1.0.4**: Versione corrente
  - Rimozione completa test framework
  - Ottimizzazione build production
  - Fix bugs BpmService
  - Miglioramento logging e error handling

---

**Ultimo aggiornamento**: Giugno 2025  
**Maintainer**: OS Services Team
