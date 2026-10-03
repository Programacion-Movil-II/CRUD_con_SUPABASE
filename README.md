# 🐾 CRUD de Mascotas - Android Java + Supabase
Aplicación móvil para Android desarrollada en **Java** que permite gestionar un registro completo de mascotas (Crear, Leer, Actualizar y Eliminar) conectada a la base de datos de **Supabase** a través de su API REST utilizando **Retrofit**.


<img width="1532" height="321" alt="image" src="https://github.com/user-attachments/assets/cd105b26-4ffe-4d8d-a74c-a00f0c81ddd5" />


<img width="301" height="644" alt="image" src="https://github.com/user-attachments/assets/291c2fd5-029c-40bb-b2f4-020af35aa687" />

<img width="303" height="658" alt="image" src="https://github.com/user-attachments/assets/47bbdab6-dd70-4c55-8261-adade7227a8b" />


##  Arquitectura y Estructura del Código

```text
com.example.crud
├── FormMascotaActivity.java  # Formulario para crear y editar mascotas
├── MainActivity.java         # Pantalla principal con la lista de mascotas
├── Mascota.java              # Modelo de datos (POJO)
├── MascotaAdapter.java       # Adaptador para renderizar elementos en el RecyclerView
├── ApiService.java           # Interfaz que define los endpoints HTTP (GET, POST, PATCH, DELETE)
└── RetrofitClient.java       # Cliente Singleton de Retrofit e inyección de la API Key
