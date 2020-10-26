 /*
     Логика по взаимодействию с XML-файлами
 */

 function findXMLBy(file, keyword) {
     $.ajax({
     	 type: 'GET',
         url: file,
     	 dataType: 'xml',
         success: function(xml) {
     	     $(xml).find('timeTable').filter(function() {
     	         return $('dayOfWeekRus', this).text() == keyword;
     	     }).find('lesson').each(appendHTML);
     	 }
     });
 }

 function appendHTML() {
     $('section').append('<div class="text-block"><h2>' +
         $(this).find('title').text() + '</h2><ul type="square"><li>Проходит ' +
         ($(this).find('isRemote').text() == 'true' ? 'дистанционно' : 'очно') + '</li><li>Начинается в ' +
         $(this).find('startTime').text() + ' и заканчивается в ' +
         $(this).find('endTime').text() + '</li><li>Номер аудитории: ' +
         $(this).find('audienceNumber').text() + '</li><li>Преподаватель: ' +
         $(this).find('lector').text() + '</li><li>Описание: ' +
         $(this).find('description').text() + '</li></ul></div>');
 }
