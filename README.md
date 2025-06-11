# 🏞️ Akıllı Su Kalitesi İzleme Sistemi (“Water Quality Project”)

> Gerçek zamanlı sensör verisi toplayan, saklayan ve görselleştiren uçtan-uca IoT ekosistemi
> **ESP32 → LoRa → MQTT → Spring Boot + PostgreSQL → WebSocket/STOMP → Statik Frontend**

---

## Projenin Amacı

* **24/7 su kalitesi izlemesi:** Sıcaklık, TDS, bulanıklık, iletkenlik vb.
* **Erken uyarı sistemi:** Kritik eşikler aşıldığında anında bildirim
* Tek kutudan çıkmış **uçtan-uca** donanım + yazılım çözümü

---

## Temel Bileşenler

| Katman           | Teknolojiler                                      | Öne Çıkanlar                                                               |
| ---------------- | ------------------------------------------------- | -------------------------------------------------------------------------- |
| **Gömülü**       | ESP32, Arduino framework, DallasTemperature, LoRa | Sensör okuma, CRC’li LoRa paketleri, WiFi Manager, MQTT publish            |
| **Veri İletimi** | LoRa + Eclipse Mosquitto 2.0                      | JSON yükü `sensors/#` topic’inde                                           |
| **Backend**      | Java 21, Spring Boot 3.4, JPA                     | MQTT aboneliği → PostgreSQL saklama, REST API + JWT, STOMP WebSocket akışı |
| **Frontend**     | HTML/CSS/JS, Chart.js, SockJS, Stomp.js           | Canlı grafik paneli, blog & admin sayfaları                                |
| **Orkestrasyon** | Docker Compose                                    | Tek komutla PostgreSQL, Mosquitto, Spring Boot, Nginx                      |

---

## Dizin Yapısı (Özet)

```
waterqualityproject/
├─ docker-compose.yml         # Tüm servis tanımları
├─ backend/                   # Spring Boot uygulaması
├─ embedded/
│  ├─ esp32/                  # Sensör düğümü (LoRa TX)
│  └─ esp32Receiver/          # Alıcı düğüm (LoRa RX + MQTT bridge)
├─ frontend/                  # Statik site + dashboard
├─ mosquitto/                 # Broker konfigürasyonu
└─ postgres-init/             # Başlangıç SQL betiği
```

---

## Veri Akış Şeması

```
[Sensörler] → ESP32 (LoRa TX)
                    │
                    ▼
            ESP32 Receiver (LoRa RX)
                    │ JSON / MQTT
                    ▼
           Eclipse Mosquitto Broker
                    │
     ┌──────────────┴──────────────┐
     │                             
Spring Boot Backend  ── WebSocket/STOMP ──► Frontend Grafikleri
 (JPA, REST, JWT)                         ▲
     │                                    │
     └────────────► PostgreSQL ◄──────────┘
```

---

## Veri Modeli (örnek)

| Alan          | Tip             | Açıklama                       |
| ------------- | --------------- | ------------------------------ |
| `id`          | `Long`          | Otomatik kimlik                |
| `type`        | `String`        | `temperature`, `tds` …         |
| `temperature` | `Double`        | Örnek sensör değeri            |
| `timestamp`   | `LocalDateTime` | Sunucu saati (Europe/Istanbul) |

---

## Güvenlik

* **JWT kimlik doğrulama** (`/api/auth/login`)
* Parolalar **BCrypt** ile hashlenir (bkz. `HashGen.java`)
* Nginx reverse-proxy’de CORS ve WebSocket ayarları hazır

---

## Kullanıcı Arayüzleri

* **Ana Site:** Proje tanıtımı, canlı sensör kartları, rapor talep modalleri
* **Gerçek Zamanlı Dashboard:** JWT giriş sonrası filtrelenebilir sıcaklık grafiği
* **Admin Paneli:** LocalStorage tabanlı demo & blog yönetimi

---

## Neden Seçmelisiniz?

* **Modüler:** Her katman ayrı servis/klasör
* **Taşınabilir:** Raspberry Pi dahil ARM/x86 üzerinde Docker ile çalışır
* **Ölçeklenebilir:** Yeni sensör eklemek; JSON şeması + JPA varlığı eklemek kadar kolay
* **Gerçek Zamanlı:** WebSocket sayesinde sayfayı yenilemeden güncel veri
* **Açık Kaynak:** Katkıya ve genişlemeye açık mimari

---

## Katkıda Bulunanlar

| İsim                       | Rol                                      |
| -------------------------- | ---------------------------------------- |
| **Eren ERTEN**             | Frontend                                 |
| **Alaaddin Can GÜRSOY**    | Backend, gömülü yazılım, sistem mimarisi |
| **Ali Osman DALKILIÇ**     | Elektronik tasarım ve entegrasyon        |
