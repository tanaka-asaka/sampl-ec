$(function() {
  
  var pagetop = $('.pagetop');

  //ボタンを非表示にする、初期設定用
  pagetop.hide();

  //画面をスクロールしたとき
  $(window).scroll(function() {
    //500pxより多くスクロールした場合
    if ($(this).scrollTop() > 500) {
      //ボタンをフェードインさせる
      pagetop.fadeIn();
    } else {
      pagetop.fadeOut();
    }
  });
  //pagetopをクリックすると
  pagetop.click(function() {
    //画面の上から0pxの所まで500ミリ秒（0.5秒）かけてアニメーションしながらスクロールする
    $('body, html').animate({ scrollTop: 0 }, 500);
    return false;
  });
});