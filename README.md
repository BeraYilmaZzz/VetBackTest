Veteriner Yönetim Sistemi

Bir veteriner kliniğinin kendi işlerini yönetebildiği API.
Veteriner çalışanı tarafından kullanılabilecek. 
Bu uygulama ile çalışan sisteme ==
veteriner doktorları kaydedecek,
doktorların çalışma günlerini (müsait günlerini) kaydedecek, saat olmadan tarih olarak kayıt yapılacak,
müşterileri kaydedecek,
müşterilere ait hayvanları kaydedecek,
hayvanlara uygulanmış aşıları tarihleriyle birlikte kaydedecek,
hayvanlar için veteriner hekimlere randevu oluşturacaklar,
randevu oluştururken tarih ve saat girilecek,
randevu oluştururken hem doktorun müsait günlerinden saat olmadan kontrol yapılmalı hem de randevu kayıtlarından tarih ve saat ile kontrol yapılmakta. Kayıtlarda çakışma olmadığı durumda randevu oluşturulmamaktadır.

API end Points;

CUSTOMER ==
ekleme- http://localhost:8081/v1/customers
silme- http://localhost:8081/v1/customers/delete/x
tümü- http://localhost:8081/v1/customers/search
güncelle- http://localhost:8081/v1/customers/update/x
name'i getir- http://localhost:8081/v1/customers/name

DOCTOR == 
ekleme- http://localhost:8081/v1/doctors
silme- http://localhost:8081/v1/doctors/delete/x
update-  http://localhost:8081/v1/doctors/update/x
tümü- http://localhost:8081/v1/doctors/search
güncelle- http://localhost:8081/v1/doctors/update/x
x'i getir- http://localhost:8081/v1/doctors/x

ANIMAL == 
ekleme- http://localhost:8081/v1/animals
silme- http://localhost:8081/v1/animals/delete/x
update- http://localhost:8081/v1/animals/update/x
xname'i getir- http://localhost:8081/v1/animals/name/xname
id'i getir- http://localhost:8081/v1/animals/get/id
hepsini getir- http://localhost:8081/v1/animals/all
customer idsi x olanı getir- http://localhost:8081/v1/animals/customer/{customerIdx}"

AVAILABLE DATE ==
ekleme- http://localhost:8081/v1/available_dates
silme- http://localhost:8081/v1/available_dates/delete/x
güncelleme- http://localhost:8081/v1/available_dates/update/x
x'i getir- http://localhost:8081/v1/available_dates/x
doctor idsi x olanı getir- http://localhost:8081/v1/available_dates/doctor/{doctorIdx}"
hepsini getir- http://localhost:8081/v1/available_dates/all

VACCINE ==
ekleme- http://localhost:8081/v1/vaccines
silme- http://localhost:8081/v1/vaccines/delete/x
güncelleme- http://localhost:8081/v1/vaccines/update/x
hepsini getir- http://localhost:8081/v1/vaccines/search
animal idsi x olanı getir- http://localhost:8081/v1/vaccines/animal/{animalIdx}"
yaklaşan tarihteki ilaçları getir- http://localhost:8081/v1/vaccines/upcoming/{startDate}/{endDate}

APPOINTMENTS ==
ekleme- http://localhost:8081/v1/appointments
silme- http://localhost:8081/v1/appointments/delete/x
hepsini getir- http://localhost:8081/v1/appointments/all
güncelleme- http://localhost:8081/v1/appointments/update/x
doctor id si x olanın date tarihindeki randevularını getir- http://localhost:8081/v1/appointments/get/doctor-date/{doctorId}/{date}"
animal id si x olanın date tarihindeki randevularını getir- http://localhost:8081/v1/appointments//filter_date_and_animal/{animalId}/{date}"

