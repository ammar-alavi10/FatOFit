firebase.initializeApp(config);
var database = firebase.database(); 

var firebaseConfig = {
    apiKey: "AIzaSyA8FSuOlhO4ZB-qs-SYt2a_qeCe_gi3gRA",
    authDomain: "fatofit-23e47.firebaseapp.com",
    databaseURL: "https://fatofit-23e47.firebaseio.com",
    storageBucket: "fatofit-23e47.appspot.com",
    messagingSenderId: "435989364946",
  };

  firebase.initializeApp(firebaseConfig);
  database=firebase.database();
  var ref=database.ref("Users")
  ref.once('value', gotData1, errData);