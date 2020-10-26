/*
 *   Entry point
 *   Здесь вызываются обработчики событий
 */
var switchedToEvenWeek = true;
var selectedDay = 'ПОНЕДЕЛЬНИК';

$('document').ready(function() {
    try {
        reloadSectionBy(selectedDay);
        $('.toggleSwitch').click(onToggleSwitch);
        $('.existing').click(onExistingLink);
        $('.text-block').click(onTextBlock);
    }
    catch(e) {
        alert(e);
    }
});

/*
 *   Обработчики событий и вспомогательные фунции
 */
function reloadSectionBy(keyword) {
    $('section').empty();
    $.getScript('scripts/xml-handler.js', function() {
        var file = switchedToEvenWeek ? 'xml/evenWeek.xml' : 'xml/oddWeek.xml';
        findXMLBy(file, keyword);
    });
    $('section').hide();
    $('section').fadeIn(800);
}

function onToggleSwitch() {
    switchedToEvenWeek = !switchedToEvenWeek;
    reloadSectionBy(selectedDay);
    if(switchedToEvenWeek) {
        $(this).removeClass('active');
        $(this).find('input').attr('checked', false);
        $(this).find('.slider').css({"-webkit-transform" : "translateX(0px)"});
    }
    else {
        $(this).addClass('active');
        $(this).find('input').attr('checked', true);
        $(this).find('.slider').css({"-webkit-transform" : "translateX(27px)"});
    }
}

function onExistingLink(event) {
    $('.existing').removeClass('active');
    $(this).addClass('active');
    selectedDay = event.target.text;
    reloadSectionBy(selectedDay);
}

function onTextBlock() {

}

