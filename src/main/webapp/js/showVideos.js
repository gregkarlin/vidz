var vid_width = 4*12;
var vid_height = 3*12;
function showVideos(key,position_index,quantity,offset) {
  for (i=1;i<=quantity;i++) {
      var rotate = (7.5*i) + offset +(position_index* -360/length);
      var translate_x =   110;
      var translate_y =  220;
    canvas.append('svg:image')
      .attr('xlink:href','../images/preview.jpeg')
      .attr('x',80)
      .attr('y',-70)
      .attr('width',vid_width)
      .attr('height',vid_height) 
      .attr('class','arm'+position_index)
      .attr('transform','rotate(' + rotate + ',' + translate_x  +',' + translate_y  + ')');
  }

}

function hideVideos(position_index) {
  $('.arm'+position_index).remove();
};
