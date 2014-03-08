var vid_width = 4*12;
var vid_height = 3*12;
function showVideo(video_file) {
	window.location.href="/movie/"+video_file;
}
function showVideos(data,key,position_index,quantity,offset) {
  for (i=1;i<=quantity;i++) {
    var rotate = offset +(position_index* -360/length);
    var second_rotate = (7.5*i) + offset +(position_index* -360/length);
    var translate_x =   110;
    var translate_y =  220;
    p = data;
    var thumb_nail = data[key][i-1]["_4"];
    var video_file = data[key][i-1]["_3"];
    var vid = canvas.append('svg:image')
    .attr('xlink:href','../images/' + thumb_nail)
    .attr('width',vid_width)
    .attr('height',vid_height) 
    .attr('x',80)
    .attr('y',-70)
    .attr('class','arm_extension')
    .attr('transform','rotate(' + rotate + ',' + translate_x  +',' + translate_y  + ')')
    .on("mousedown",function(d){showVideo(video_file)});
    
    canvas.append("svg:text")
      .attr("class","arm_title")
      .attr("transform","translate(60,190)")
      .text(key)
    vidOrbiter(vid,i,position_index,quantity,translate_x,translate_y,500,0,Math.random())
  }
}

function hideVideos() {
  $('.arm_extension').remove();
  $('.arm_title').remove()
};

function vidOrbiter(vid,i,position_index,quantity,x,y,orbit,angle,random_velocity) {
  var rotation = ((7.5*i) + (10000000 *(Math.cos(orbit)/Math.pow(orbit,2)))) + offset +(position_index* -360/length)
  var radius = 1 + 1.1 * random_velocity;
  var translate_x = x + (Math.cos(angle) * radius)*.6*position_index;
  var translate_y = y + (Math.sin(angle) * radius)*.6*position_index;
  var rotation_duration = Math.floor(10*random_velocity)
  angle = angle + .1;
  orbit = Math.min((orbit + .5*orbit),10000);
  vid.transition()
  .duration(5 *Math.random() )
  .attr('transform','rotate(' + rotation + ',' + translate_x  +',' + translate_y  + ')')
  .each('end',function(){vidOrbiter(d3.select(this),i,position_index,quantity,x,y,orbit,angle,random_velocity)});
}
