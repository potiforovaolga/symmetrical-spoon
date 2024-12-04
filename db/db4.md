### db.users.drop()                                                                                                 
true                                                                                                                    

### show collections                                                                                                
messages                                                                                                                

###db.messages.find()                                                                                            
[
{
   _id: ObjectId('65fc13e87b8dc365ad72b51a'),                                                                          
    login: 'user1',                                                                                                     
    message: 'Hello, how are you?'                                                                                      
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b519'),                                                                          
    login: 'user2',                                                                                                     
    message: 'Hi there!'                                                                                                
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b51a'),                                                                          
    login: 'user3',                                                                                                     
    message: 'Nice to meet you.'                                                                                        
  }                                                                                                                    
  ]                                                                                                                      

###db.messages.findOneAndReplace({}, {'login': 'user0'})                                                           
{
  _id: ObjectId('65fc13e87b8dc365ad72b518'),                                                                            
  login: 'user1',                                                                                                       
  message: 'Hello, how are you?'                                                                                        
}                                                                                                                       

###db.messages.find()                                                                                            
[
{
   _id: ObjectId('65fc13e87b8dc365ad72b51a'),                                                                          
    login: 'user0',  
                                                                                                       },                                                                                                                     
 {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b519'),                                                                          
    login: 'user2',                                                                                                     
    message: 'Hi there!'                                                                                                
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b51a'),                                                                          
    login: 'user3',                                                                                                     
    message: 'Nice to meet you.'                                                                                        
  }                                                                                                                    
  ]                                                                                                                      


###db.messages.findOneAndDelete({}, {'login': 'user0'})                                                            
{
  _id: ObjectId('65fc13e87b8dc365ad72b518'),                                                                            
  login: 'user0'}                                                                                                       

###db.messages.find()                                                                                            
[
  {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b519'),                                                                          
    login: 'user2',                                                                                                     
    message: 'Hi there!'                                                                                                
  },                                                                                                                    
  {                                                                                                                     
    _id: ObjectId('65fc13e87b8dc365ad72b51a'),                                                                          
    login: 'user3',                                                                                                     
    message: 'Nice to meet you.'                                                                                        
  }                                                                                                                    
  ]                                                                                                                      
