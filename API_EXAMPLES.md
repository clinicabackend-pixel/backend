# 🔌 Ejemplos de Uso de la API - Clínica Jurídica

Este documento contiene ejemplos completos de cómo consumir la API usando diferentes herramientas.

---

## 📋 Tabla de Contenidos

1. [cURL (Terminal)](#curl-terminal)
2. [HTTPie (Terminal)](#httpie-terminal)
3. [Postman](#postman)
4. [JavaScript (Fetch API)](#javascript-fetch-api)
5. [Python (Requests)](#python-requests)
6. [Java (HttpClient)](#java-httpclient)

---

## 🔧 cURL (Terminal)

### Endpoint: GET / (Bienvenida)

```bash
curl http://localhost:8080/
```

**Respuesta:**
```
Welcome to the Clinica Juridica Backend!
```

---

### Endpoint: GET /api/casos/solicitante/{id}

```bash
curl -X GET http://localhost:8080/api/casos/solicitante/V-12345678 \
  -H "Accept: application/json"
```

**Respuesta:**
```json
[
  {
    "numCaso": "CASO-EJEMPLO-001",
    "sintesis": "Solicitud de asesoría legal para proceso de divorcio contencioso con menores de edad",
    "estatus": "ABIERTO",
    "fechaRecepcion": "2024-01-15",
    "idSolicitante": "V-12345678",
    "tramite": "Divorcio",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }
]
```

---

### Endpoint: POST /api/casos

```bash
curl -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "sintesis": "Consulta legal sobre pensión alimenticia y custodia compartida de menores",
    "idSolicitante": "V-12345678",
    "tramite": "Pensión Alimenticia",
    "cantBeneficiarios": 2,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }'
```

**Respuesta (201 CREATED):**
```json
{
  "numCaso": "CASO-20241125-143022-456",
  "sintesis": "Consulta legal sobre pensión alimenticia y custodia compartida de menores",
  "estatus": "ABIERTO",
  "fechaRecepcion": "2024-11-25",
  "idSolicitante": "V-12345678",
  "tramite": "Pensión Alimenticia",
  "cantBeneficiarios": 2,
  "idCentro": 1,
  "idAmbitoLegal": 3
}
```

---

### cURL con formato bonito (usando jq)

```bash
# Instalar jq: sudo apt install jq  (Linux) o brew install jq (Mac)

# GET con formato
curl -s http://localhost:8080/api/casos/solicitante/V-12345678 | jq .

# POST con formato
curl -s -X POST http://localhost:8080/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Caso de prueba",
    "idSolicitante": "V-12345678",
    "tramite": "Divorcio",
    "cantBeneficiarios": 1,
    "idCentro": 1,
    "idAmbitoLegal": 3
  }' | jq .
```

---

## 🌐 HTTPie (Terminal)

HTTPie es una alternativa más amigable a cURL.

### Instalación

```bash
# Linux/Mac
pip install httpie

# Mac (Homebrew)
brew install httpie
```

### Ejemplos

```bash
# GET Bienvenida
http GET localhost:8080/

# GET Casos por solicitante
http GET localhost:8080/api/casos/solicitante/V-12345678

# POST Crear caso
http POST localhost:8080/api/casos \
  sintesis="Consulta sobre derecho laboral" \
  idSolicitante="V-12345678" \
  tramite="Despido Injustificado" \
  cantBeneficiarios:=1 \
  idCentro:=1 \
  idAmbitoLegal:=4
```

---

## 📮 Postman

### Colección de Postman

#### 1. GET Bienvenida

- **Method:** GET
- **URL:** `http://localhost:8080/`
- **Headers:** Ninguno necesario

#### 2. GET Casos por Solicitante

- **Method:** GET
- **URL:** `http://localhost:8080/api/casos/solicitante/V-12345678`
- **Headers:**
  - `Accept: application/json`

#### 3. POST Crear Caso

- **Method:** POST
- **URL:** `http://localhost:8080/api/casos`
- **Headers:**
  - `Content-Type: application/json`
  - `Accept: application/json`
- **Body (raw JSON):**
```json
{
  "sintesis": "Consulta legal sobre derecho mercantil",
  "idSolicitante": "V-12345678",
  "tramite": "Constitución de Sociedad",
  "cantBeneficiarios": 1,
  "idCentro": 1,
  "idAmbitoLegal": 5
}
```

### Exportar Colección de Postman

```json
{
  "info": {
    "name": "Clínica Jurídica API",
    "description": "API REST para gestión de casos jurídicos",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": [""]
        }
      }
    },
    {
      "name": "Listar Casos por Solicitante",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Accept",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/api/casos/solicitante/V-12345678",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "casos", "solicitante", "V-12345678"]
        }
      }
    },
    {
      "name": "Crear Caso",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"sintesis\": \"Consulta legal sobre derecho penal\",\n  \"idSolicitante\": \"V-12345678\",\n  \"tramite\": \"Defensa Penal\",\n  \"cantBeneficiarios\": 1,\n  \"idCentro\": 1,\n  \"idAmbitoLegal\": 2\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/casos",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "casos"]
        }
      }
    }
  ]
}
```

---

## 🟨 JavaScript (Fetch API)

### GET Casos por Solicitante

```javascript
// Función async/await
async function obtenerCasos(idSolicitante) {
  try {
    const response = await fetch(
      `http://localhost:8080/api/casos/solicitante/${idSolicitante}`,
      {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        }
      }
    );
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const casos = await response.json();
    console.log('Casos:', casos);
    return casos;
  } catch (error) {
    console.error('Error al obtener casos:', error);
    throw error;
  }
}

// Uso
obtenerCasos('V-12345678');
```

### POST Crear Caso

```javascript
async function crearCaso(datosCaso) {
  try {
    const response = await fetch('http://localhost:8080/api/casos', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(datosCaso)
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const casoCreado = await response.json();
    console.log('Caso creado:', casoCreado);
    return casoCreado;
  } catch (error) {
    console.error('Error al crear caso:', error);
    throw error;
  }
}

// Uso
const nuevoCaso = {
  sintesis: "Consulta sobre derecho civil",
  idSolicitante: "V-12345678",
  tramite: "Sucesión",
  cantBeneficiarios: 3,
  idCentro: 1,
  idAmbitoLegal: 1
};

crearCaso(nuevoCaso);
```

### React Hook Personalizado

```javascript
import { useState, useEffect } from 'react';

function useCasosPorSolicitante(idSolicitante) {
  const [casos, setCasos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCasos = async () => {
      try {
        setLoading(true);
        const response = await fetch(
          `http://localhost:8080/api/casos/solicitante/${idSolicitante}`
        );
        
        if (!response.ok) {
          throw new Error('Error al obtener los casos');
        }
        
        const data = await response.json();
        setCasos(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    if (idSolicitante) {
      fetchCasos();
    }
  }, [idSolicitante]);

  return { casos, loading, error };
}

// Uso en componente
function CasosComponent({ solicitanteId }) {
  const { casos, loading, error } = useCasosPorSolicitante(solicitanteId);

  if (loading) return <div>Cargando...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Casos del Solicitante</h2>
      {casos.map(caso => (
        <div key={caso.numCaso}>
          <h3>{caso.numCaso}</h3>
          <p>{caso.sintesis}</p>
          <p>Estado: {caso.estatus}</p>
        </div>
      ))}
    </div>
  );
}
```

---

## 🐍 Python (Requests)

### Instalación

```bash
pip install requests
```

### GET Casos por Solicitante

```python
import requests
import json

def obtener_casos(id_solicitante):
    url = f"http://localhost:8080/api/casos/solicitante/{id_solicitante}"
    
    try:
        response = requests.get(url, headers={'Accept': 'application/json'})
        response.raise_for_status()  # Lanza excepción si hay error HTTP
        
        casos = response.json()
        print(f"Casos encontrados: {len(casos)}")
        print(json.dumps(casos, indent=2, ensure_ascii=False))
        return casos
        
    except requests.exceptions.RequestException as e:
        print(f"Error al obtener casos: {e}")
        return None

# Uso
casos = obtener_casos("V-12345678")
```

### POST Crear Caso

```python
import requests
import json
from datetime import datetime

def crear_caso(datos_caso):
    url = "http://localhost:8080/api/casos"
    headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
    
    try:
        response = requests.post(url, json=datos_caso, headers=headers)
        response.raise_for_status()
        
        caso_creado = response.json()
        print("✅ Caso creado exitosamente!")
        print(json.dumps(caso_creado, indent=2, ensure_ascii=False))
        return caso_creado
        
    except requests.exceptions.RequestException as e:
        print(f"❌ Error al crear caso: {e}")
        if hasattr(e.response, 'text'):
            print(f"Detalle: {e.response.text}")
        return None

# Uso
nuevo_caso = {
    "sintesis": "Consulta sobre derecho laboral - despido injustificado",
    "idSolicitante": "V-23456789",
    "tramite": "Despido Injustificado",
    "cantBeneficiarios": 1,
    "idCentro": 2,
    "idAmbitoLegal": 4
}

caso = crear_caso(nuevo_caso)
```

### Script Completo (Python)

```python
#!/usr/bin/env python3
"""
Script de prueba para la API de Clínica Jurídica
"""

import requests
import json
from typing import List, Dict, Optional

BASE_URL = "http://localhost:8080"

class ClinicaJuridicaAPI:
    
    def __init__(self, base_url: str = BASE_URL):
        self.base_url = base_url
        self.session = requests.Session()
        self.session.headers.update({'Accept': 'application/json'})
    
    def health_check(self) -> str:
        """Verifica que la API esté funcionando"""
        response = self.session.get(f"{self.base_url}/")
        response.raise_for_status()
        return response.text
    
    def obtener_casos_solicitante(self, id_solicitante: str) -> List[Dict]:
        """Obtiene todos los casos de un solicitante"""
        url = f"{self.base_url}/api/casos/solicitante/{id_solicitante}"
        response = self.session.get(url)
        response.raise_for_status()
        return response.json()
    
    def crear_caso(self, sintesis: str, id_solicitante: str, 
                   tramite: str, cant_beneficiarios: int = 1,
                   id_centro: int = 1, id_ambito_legal: int = 1) -> Dict:
        """Crea un nuevo caso"""
        url = f"{self.base_url}/api/casos"
        
        datos = {
            "sintesis": sintesis,
            "idSolicitante": id_solicitante,
            "tramite": tramite,
            "cantBeneficiarios": cant_beneficiarios,
            "idCentro": id_centro,
            "idAmbitoLegal": id_ambito_legal
        }
        
        response = self.session.post(
            url, 
            json=datos,
            headers={'Content-Type': 'application/json'}
        )
        response.raise_for_status()
        return response.json()

# Uso
if __name__ == "__main__":
    api = ClinicaJuridicaAPI()
    
    print("🔍 Verificando conexión...")
    print(api.health_check())
    print()
    
    print("📋 Obteniendo casos del solicitante V-12345678...")
    casos = api.obtener_casos_solicitante("V-12345678")
    print(f"Total de casos: {len(casos)}")
    print(json.dumps(casos, indent=2, ensure_ascii=False))
    print()
    
    print("➕ Creando nuevo caso...")
    nuevo_caso = api.crear_caso(
        sintesis="Consulta sobre pensión alimenticia",
        id_solicitante="V-12345678",
        tramite="Pensión Alimenticia",
        cant_beneficiarios=2,
        id_centro=1,
        id_ambito_legal=3
    )
    print("✅ Caso creado:")
    print(json.dumps(nuevo_caso, indent=2, ensure_ascii=False))
```

---

## ☕ Java (HttpClient)

### Java 11+ HttpClient

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClinicaJuridicaClient {
    
    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient httpClient;
    
    public ClinicaJuridicaClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    
    // GET Casos por solicitante
    public String obtenerCasos(String idSolicitante) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/casos/solicitante/" + idSolicitante))
                .header("Accept", "application/json")
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error: " + response.statusCode());
        }
    }
    
    // POST Crear caso
    public String crearCaso(String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/casos"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        
        if (response.statusCode() == 201) {
            return response.body();
        } else {
            throw new RuntimeException("Error: " + response.statusCode());
        }
    }
    
    public static void main(String[] args) {
        try {
            ClinicaJuridicaClient client = new ClinicaJuridicaClient();
            
            // Obtener casos
            System.out.println("Obteniendo casos...");
            String casos = client.obtenerCasos("V-12345678");
            System.out.println(casos);
            
            // Crear caso
            System.out.println("\nCreando nuevo caso...");
            String jsonCaso = """
                {
                  "sintesis": "Consulta legal sobre derecho civil",
                  "idSolicitante": "V-12345678",
                  "tramite": "Sucesión",
                  "cantBeneficiarios": 2,
                  "idCentro": 1,
                  "idAmbitoLegal": 1
                }
                """;
            
            String casoCreado = client.crearCaso(jsonCaso);
            System.out.println(casoCreado);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## 🧪 Scripts de Prueba

### Bash Script para Pruebas Automatizadas

```bash
#!/bin/bash

BASE_URL="http://localhost:8080"

echo "========================================="
echo "   PRUEBAS DE API - CLÍNICA JURÍDICA"
echo "========================================="
echo ""

# Test 1: Health Check
echo "🔍 Test 1: Health Check"
curl -s $BASE_URL/
echo -e "\n"

# Test 2: Listar casos
echo "📋 Test 2: Listar casos del solicitante V-12345678"
curl -s $BASE_URL/api/casos/solicitante/V-12345678 | jq .
echo ""

# Test 3: Crear caso
echo "➕ Test 3: Crear nuevo caso"
curl -s -X POST $BASE_URL/api/casos \
  -H "Content-Type: application/json" \
  -d '{
    "sintesis": "Caso de prueba automatizada",
    "idSolicitante": "V-12345678",
    "tramite": "Prueba",
    "cantBeneficiarios": 1,
    "idCentro": 1,
    "idAmbitoLegal": 1
  }' | jq .
echo ""

echo "========================================="
echo "   ✅ PRUEBAS COMPLETADAS"
echo "========================================="
```

---

## 📊 Códigos de Respuesta HTTP

| Código | Significado | Cuándo se usa |
|--------|-------------|---------------|
| 200 OK | Exitoso | GET exitoso |
| 201 CREATED | Creado | POST exitoso (recurso creado) |
| 400 BAD REQUEST | Petición inválida | Datos de entrada inválidos |
| 404 NOT FOUND | No encontrado | Recurso no existe |
| 500 INTERNAL SERVER ERROR | Error del servidor | Error interno de la aplicación |

---

**Nota**: Todos los ejemplos asumen que la aplicación está corriendo en `localhost:8080`. Ajusta la URL según tu configuración.

