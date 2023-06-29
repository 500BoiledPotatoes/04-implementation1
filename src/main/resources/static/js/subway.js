// $(function(){
//     //翻译
//     $("#goTran").click(function(){
//         var query = $("#sendMessage").val();
//         if (query.toString()=='') {
//             return;
//         }
//         var str1 = appid + query + salt +key;
//         var sign = MD5(str1);
//         $.ajax({
//             url: 'http://api.fanyi.baidu.com/api/trans/vip/translate',
//             type: 'get',
//             dataType: 'jsonp',
//             data: {
//                 q: query,
//                 appid: appid,
//                 salt: salt,
//                 from: from,
//                 to: to,
//                 sign: sign
//             },
//             success: function (data) {
//                 show=document.getElementById('showResponseValue');
//                 show.value=data.trans_result[0].dst;
//                 ResFrom=data.from;
//                 ResTo=data.to;
//                 FType=document.getElementById('inputLanguageType');
//                 TType=document.getElementById('showLanguageType');
//                 FType.innerHTML='语言类型:'+ResFrom;
//                 TType.innerHTML='语言类型:'+ResTo;
//             }
//         });
//     });
// });
// $(function () {
//     $('#sendMessage').keydown(function (event) {
//         if (event.keyCode==13){
//             var query = $("#sendMessage").val();
//             if (query.toString()=='') {return;}
//             var str1 = appid + query + salt +key;
//             var sign = MD5(str1);
//             $.ajax({
//                 url: 'http://api.fanyi.baidu.com/api/trans/vip/translate',
//                 type: 'get',
//                 dataType: 'jsonp',
//                 data: {
//                     q: query,
//                     appid: appid,
//                     salt: salt,
//                     from: from,
//                     to: to,
//                     sign: sign
//                 },
//                 success: function (data) {
//                     show=document.getElementById('showResponseValue');
//                     show.value=data.trans_result[0].dst;
//
//                     ResFrom=data.from;
//                     ResTo=data.to;
//                     FType=document.getElementById('inputLanguageType');
//                     TType=document.getElementById('showLanguageType');
//                     FType.innerHTML='语言类型:'+ResFrom;
//                     TType.innerHTML='语言类型:'+ResTo;
//                 }
//             });
//         }
//     });
// });
//
//
// </script>