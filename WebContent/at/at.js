var emojis = [
    "smile", "iphone", "girl", "smiley", "heart", "kiss", "copyright", "coffee",
    "a", "ab", "airplane", "alien", "ambulance", "angel", "anger", "angry",
    "arrow_forward", "arrow_left", "arrow_lower_left", "arrow_lower_right",
    "arrow_right", "arrow_up", "arrow_upper_left", "arrow_upper_right",
    "art", "astonished", "atm", "b", "baby", "baby_chick", "baby_symbol",
    "balloon", "bamboo", "bank", "barber", "baseball", "basketball", "bath",
    "bear", "beer", "beers", "beginner", "bell", "bento", "bike", "bikini",
    "bird", "birthday", "black_square", "blue_car", "blue_heart", "blush",
    "boar", "boat", "bomb", "book", "boot", "bouquet", "bow", "bowtie",
    "boy", "bread", "briefcase", "broken_heart", "bug", "bulb",
    "person_with_blond_hair", "phone", "pig", "pill", "pisces", "plus1",
    "point_down", "point_left", "point_right", "point_up", "point_up_2",
    "police_car", "poop", "post_office", "postbox", "pray", "princess",
    "punch", "purple_heart", "question", "rabbit", "racehorse", "radio",
    "up", "us", "v", "vhs", "vibration_mode", "virgo", "vs", "walking",
    "warning", "watermelon", "wave", "wc", "wedding", "whale", "wheelchair",
    "white_square", "wind_chime", "wink", "wink2", "wolf", "woman",
    "womans_hat", "womens", "x", "yellow_heart", "zap", "zzz", "+1",
    "-1"
]
var namess = ["佘诗曼","佘卫超","冯寿永","Jacob","Isabella","Ethan","Emma","Michael","Olivia","Alexander","Sophia","William","Ava","Joshua","Emily","Daniel","Madison","Jayden","Abigail","Noah","Chloe","你好","你你你"];

var namess = $.map(namess,function(value,i) {
    return {'id':i,'name':value,'email':value+"@email.com"};
});
var trips = ["北京上海昆明10日游","北京上海曼谷20日行","东南亚4国20日行"];

var trips = $.map(trips,function(value,i) {
    return {'id':i,'name':value};
});
var emojis = $.map(emojis, function(value, i) {return {key: value, name:value}});

$(function(){
    $inputor = $('#inputors').atwho({
        at: "@",
        data: namess,
        //data: "assets/data.json",
        tpl: "<li data-value='@${name}'>${name} <small>${email}</small></li>",
        callbacks: {
            //before_save: function(data) {
            	//alert(data.name);
                //return this.call_default("before_save", data.names);
            //}
        	before_insert: function(value, $li) {  
        		//alert(value);
        		return value; 
        	}
    		//,
        	//matcher: function(flag, subtext, should_start_with_space) {  
            //     var match, regexp;  
            //     flag = flag.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");  
            //     if (should_start_with_space) {  
            //         flag = '(?:^|\\s)' + flag;  
            //     }  
            //    
            //     regexp = new RegExp(flag + '([A-Za-z0-9_\+\-]*)$|' + flag + '([^\\x00-\\xff]*)$', 'gi');  
             //    match = regexp.exec(subtext);  
             //    if (match) {  
              //       var i;  
              //       commaCount = 0;  
              //       for(i=0;i<subtext.length;i++){  
              //           if(subtext.charAt(i) == ","){  
              //               commaCount++;  
              //           }  
              //       }  
               //      return subtext;  
              //   } else {  
              //   return null;  
              //   }  
             //}
        }
    });
    //$inputor.caret('pos', 47);
    $inputor.focus().atwho('run');

    $('#editable').atwho({
        at: ":",
        data: emojis,
        tpl:"<li data-value='${key}'>${name} <img src='http://a248.e.akamai.net/assets.github.com/images/icons/emoji/${name}.png'  height='20' width='20' /></li>",
        insert_tpl: "<img src='http://a248.e.akamai.net/assets.github.com/images/icons/emoji/${name}.png'  height='20' width='20' />",
        show_the_at: false
    }).atwho({
        at: "@",
        data: namess,
        tpl: "<li data-value='@${name}'>${name} <small>${email}</small></li>"
    }).atwho({
        at: "#",
        data: trips,
        tpl: "<li data-value='#${name}'>${name}</li>"
    });
    
    
});
