# Despliegue en Render (Docker)

Pasos rápidos para desplegar este proyecto Spring Boot en Render usando el `Dockerfile` incluido.

- Requisitos: repositorio en GitHub/GitLab/Bitbucket conectado a Render.

1) Asegúrate de que el código compila localmente:

```bash
./mvnw -B -DskipTests package
```

2) Build local de la imagen Docker y correrla:

```bash
docker build -t proyecto-db-backend:local .
docker run -p 8080:8080 -e PORT=8080 proyecto-db-backend:local
```

3) Despliegue en Render:

- Conecta el repositorio a Render.
- Crea un nuevo servicio: `Web Service` y elige `Docker` como ambiente de build (Render usará el `Dockerfile`).
- Si quieres ajustar memoria/plan, edítalo en la UI de Render.

Notas importantes:
- El contenedor usa `--server.port=${PORT:-8080}` para respetar la variable `PORT` que Render proporciona. No es necesario cambiar `application.properties`.
- Si necesitas variables de entorno (credenciales, URL de BD), añádelas en la sección `Environment` del servicio en Render.
