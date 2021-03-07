db = db.getSiblingDB('bank');
db.createCollection('users');

db.users.insertMany([
    { "_id": ObjectId("60443672a1d31c3a4617d90d"), "name": "USER1", "lastName": "SURNAME1", "phone": "687635109", "contacts": [{"contactName": "contact1", "phone": "123456789"}, {"contactName": "contact2", "phone": "987654321"}] },
    { "_id": ObjectId("60443672a1d31c3a4617d90e"), "name": "USER2", "lastName": "SURNAME2", "phone": "632758047", "contacts": [{"contactName": "contact1", "phone": "123456789"}, {"contactName": "contact3", "phone": "677854499"}] }
]);