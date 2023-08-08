## Mongodb commands

Command to delete all item from collection
```shell
# select test db 
use test 

# delete all in collection category
db.categories.deleteMany({})

# print all document in collection categories
db.categories.find({})

# Get all the collection 
db.getCollectionNames()
```

Category Collection Item 
```javascript
{
  _id: ObjectId("64b27643e40469544159eb35"),
  subcategories: [],
  name: 'Fashion',
  _class: 'com.example.mongodb.document.CategoryGroup'
}
```

### Script to replace DBRef and get actual parameter for below document in mongodb 
```json
{
  "_id": {
    "$oid": "64b27d60017f5520f4b879f4"
  },
  "orderNumber": "12345",
  "customer": {
    "$ref": "customers",
    "$id": {
      "$oid": "64b27d60017f5520f4b879f3"
    }
  },
  "_class": "com.example.mongodb.document.Order"
}
```
Answer script for same is 
```shell
db.orders.aggregate([
  {
    $lookup: {
      from: "customers",
      localField: "customer.$id",
      foreignField: "_id",
      as: "customer"
    }
  },
  {
    $unwind: "$customer"
  },
  {
    $match: {
      _id: ObjectId("64b27e98fc323421513fb0ad") // Specify the _id of the order document you want to fetch
    }
  }
])
```
script result 
```javascript
{
  _id: ObjectId("64b27e98fc323421513fb0ad"),
  orderNumber: '12345',
  customer: {
    _id: ObjectId("64b27e98fc323421513fb0ac"),
    name: 'John Doe',
    _class: 'com.example.mongodb.document.Customer'
  },
  _class: 'com.example.mongodb.document.Order'
}
```

## Starting mongodb locally 
mongod
```javascript
1. Check the size of document in db
bsonsize(db.groceryitems.findOne( {  _id: ObjectId("64ca948bf9b3276c0fad843a") } ) )
2. 
```





