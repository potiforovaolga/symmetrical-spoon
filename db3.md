###use testdb
db.createCollection("messages")

db.messages.insertMany([
  { login: "user1", message: "Hello, how are you?" },
  { login: "user2", message: "Hi there!" },
  { login: "user3", message: "Nice to meet you." }
])
db.messages.find()
 {                                                                                                                     
    _id: ObjectId('65fab37bba660f778cfd43e5'),                                                                          
    login: 'user1',                                                                                                     
    message: 'Hello, how are you?'                                                                                      
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId(                                                                                                      
  {                                                                                                                     
    _id: ObjectId('65fab37bba660f778cfd43e6'),                                                                          
    login: 'user2',                                                                                                     
    message: 'Hi there!'                                                                                                
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fab37bba660f778cfd43e7'),                                                                          
    login: 'user3',                                                                                                     
message: 'Nice to meet you.' 
  }                                                                                                                    
  

###db.createCollection("users")

db.users.insertMany([
   { login: "user1", password: "password1" },
   { login: "user2", password: "password2" },
   { login: "user3", password: "password3" }
])
db.users.find()
{
    _id: ObjectId('65fb11491ce61e08c5b636f1'),                                                                          
    login: 'user1',                                                                                                     
    password: 'password1'                                                                                               
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fb11491ce61e08c5b636f2'                                                                            
   login: 'user2',                                                                                                     
    password: 'password2'                                                                                               
  },        
{
    _id: ObjectId('65fb11491ce61e08c5b636f1'),                                                                          
    login: 'user3',                                                                                                     
    password: 'password3'                                                                                               
  },                                                                                                                    

###db.users.aggregate([{ $lookup: { from: 'messages', localField: 'login', foreignField: 'login', as: 'message'}}])

[                                                                                                                       
  {                                                                                                                     
    _id: ObjectId('65fc140e7b8dc365ad72b51e'),                                                                          
    login: 'user1',                                                                                                     
    password: 'password1',                                                                                              
    message: [                                                                                                          
      {                                                                                                                 
        _id: ObjectId('65fc13e87b8dc365ad72b518'),                                                                      
        login: 'user1',                                                                                                 
        message: 'Hello, how are you?'                                                                                  
      },                                                                                                                
      {                                                                                                                 
        _id: ObjectId('65fc13ec7b8dc365ad72b51b'),                                                                      
        login:                                                                                                          
        login: 'user2',                                                                                                 
        message: 'Hi there!'                                                                                            
      },                                                                                                                
      {                                                                                                                 
        _id: ObjectId('65fc13ec7b8dc365ad72b51c'),                                                                      
        login: 'user2',                                                                                                 
        message: 'Hi there!'                                                                                            
      }                                                                                                                 
    ]                                                                                                                   
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc140e7b8dc365ad72b520'),                                                                          
    login: 'user3',                                                                                                     
    password: 'password3',                                                                                              
  },                                                                                                                    
    _id: ObjectId('65fc14127b8dc365ad72b521'),                                                                          
login:    
        _id: ObjectId('65fc13ec7b8dc365ad72b51b'),                                                                      
        login: 'user1',                                                                                                 
        message: 'Hello, how are you?'                                                                                  
      }                                                                                                                 
    ]                                                                                                                   
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc14127b8dc365ad72b522'),                                                                          
    login: 'user2',                                                                                                     
    password: 'password2',                                                                                              
    message: [                                                                                                          
      {                                                                                                                 
        _id: ObjectId('65fc13e87b8dc365ad72b519'),                                                                      
    _id: ObjectId('65fc14127b8dc365ad72b523'),                                                                          
        login: 'user3',                                                                                                 
        message: 'Nice to meet you.'                                                                                    
      },                                                                                                                
      {                                                                                                                 
        _id: ObjectId('65fc13ec7b8dc365ad72b51d'),                                                                      
        login: 'user3',                                                                                                 
        message: 'Nice to meet you.'                                                                                    
      }                                                                                                                 
    ]                                                                                                                   
  }                                                                                                                     
]                                                                                                                       
