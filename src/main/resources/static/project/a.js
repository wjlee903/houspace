// 슬릭 슬라이더 


// $( '.slider' ).slick( {
//     autoplay: true,   //오토스크롤 
//     autoplaySpeed: 2500,  //오토스크롤 시간
//     speed : 800,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
//     dots : true, 		// 스크롤바 아래 점으로 페이지네이션 여부
//     dotsClass : "slick-dots", 	//아래 나오는 페이지네이션(점) css class 지정
//     draggable : true, 	//드래그 가능 여부 
//   } );

console.clear();

// 상단 메인 배너 슬라이더
$('.slick > .page-btns > .page-btn').click(function(){
    let $clicked = $(this);
    let $slider = $(this).closest('.slick');
    
    let index = $(this).index();
    let isLeft = index == 0;
    console.log(index);
    
    let $current = $slider.find('.slide > .bn.active');
    let $post;
    
    if ( isLeft ){
        $post = $current.prev();
    }
    else {
        $post = $current.next();
    }
    // console.log($post.length);

    console.log("isLeft: "+isLeft);
    console.log("$post: "+$(this).closest('.slide'));
    
    if ( $post.length == 0 ){
        if ( isLeft ){
            $post = $slider.find('.slide > .bn:last-child');
        }
        else {
            $post = $slider.find('.slide > .bn:first-child');
        }
    }
    
    $current.removeClass('active');
    $post.addClass('active');
    
    updateCurrentPageNumber();
});

setInterval(function(){
    $('.slick > .page-btns > .next-btn').click();
}, 5000);

// 슬라이더 페이지 번호 지정
function pageNumber__Init(){
    // 전채 배너 페이지 갯수 세팅해서 .slider 에 'data-slide-total' 넣기
    let totalSlideNo = $('.slick > .slide > .bn').length;
    // console.log(totalSlideNo);
    
    $('.slick').attr('data-slide-total', totalSlideNo);
    
    // 각 배너 페이지 번호 매기기
    $('.slick > .slide > .bn').each(function(index, node){
        $(node).attr('data-slide-no', index + 1);
        // console.log(node);
    });
};

pageNumber__Init();

// 슬라이더 이동시 페이지 번호 변경
function updateCurrentPageNumber(){
    let totalSlideNo = $('.slick ').attr('data-slide-total');
    let currentSlideNo = $('.slick > .slide > .bn.active').attr('data-slide-no');
    
    $('.slick > .page-btns > .page-no > .total-slide-no').html(totalSlideNo);
    $('.slick > .page-btns > .page-no > .current-slide-no').html(currentSlideNo);
};

updateCurrentPageNumber()