辅助的自定义view系列，会把我做的最新的有趣的自定view分享给大家，一起学习！大家有什么特别点的自定义也可以跟我一起讨论呢。
虽然我开始转去后端开发了，但是写下特别的自定义view还是可以的呢。

下面就罗列下这个库的东西咯：

这里还是不放图片了，github访问太难显示了。我给我的博客吧，快很多。[Android-自定义View学习分享](http://blog.csdn.net/jack__frost/article/details/53669049)
***

#### （1）上面两个仿美团上方栏的效果：
##### **第一个方案的** 是xml中用radiobutton强制写死了大概多少个滑动页面的引导小圆点，这种方案可见复用性不高。
##### **第二种方案**是我反编译了一下美团，看了下他们的大致布局，以及耦合的代码，实现的仿造。大致过程还是跟第一个方案相同，不过在小圆点的实现，参考它们的多重复用性方案。就是用一个小圆点数组，在controller那里实现绑定每个gridview。
#### （2）自定义Textview的字体，我以前写过了。[Android-自定义TextView（彩色字体与霓虹灯字体以及TextView的多项字体效果）](http://blog.csdn.net/jack__frost/article/details/52279374)
#### （3）手动控制圆和圆形进度条也写过啦。 [Android-自定义view之圆（选择程度圆以及进度圆）](http://blog.csdn.net/jack__frost/article/details/52279036)
#### （4）圆形头像也是写过了。 [ Android-解析自定义view之圆形头像的各类方案](http://blog.csdn.net/jack__frost/article/details/52343929)
#### （5）卫星导航栏。是以前项目移植过来的一个东西。很有趣的一个东西。重点是：重写relativelayout和用viewgroup实现自己的卫星布局。过段时间会针对这个写下文章。
#### （6）流式布局。[鸿洋大大讲得很好](http://blog.csdn.net/lmj623565791/article/details/38352503/)
#### （7）水波纹。嘻嘻，这个要求是往下兼容，我用自己思路实现了一个，虽然很多人做项目都是照搬v7包，然后直接往下兼容，可是这种自定义view的会给我们加深认识安卓的底层绘图机制，继承机制的理解呀。
#### （8）图片轮播，这个很多人在开发的时候也是照搬第三方的库的，然而并不好，就不多说了。我们来讨论下它的核心思路：（具体看源码有详细的思路解释）
#### （9）recyclerview，鸿洋大大的完美解释。[鸿洋大大的recyclerview](http://blog.csdn.net/lmj623565791/article/details/45059587)
##### 有能力的朋友，最好也把鸿洋大大的recyclerview封装也看了，我在项目中用了，阅读大大的思路真的真的很棒！！[鸿洋大大的recyclerview封装](http://blog.csdn.net/lmj623565791/article/details/51118836/)
#### （10）接下来就是圆形菜单的，我基于鸿洋大大的一些思路根据实际需求做了一个限制级的圆形菜单。以前也解析过啦。[ Android-自定义view之圆形与“半圆形”菜单](http://blog.csdn.net/jack__frost/article/details/52965905)
#### （**附加的工具类**）另外在这个库中，可以看到我用的工具类（utils包内），里面均有详细介绍，（DensityUtil类）获取设备的高度和宽度，以及提供dp和px单位之间的转换；（ReadBitmap类）加载本地图片使用的工具类，防止oom；（SavePhoto类）保存bitmap到本地文件,用途是可以配合鲁班压缩框架，进行对本地图片压缩然后上传；（TypefaceUtils类）字体加载工具类，使用枚举实现单例模式。
***
***
***

### 喜欢的给下star或者fork咯，如果有朋友告诉我要写些特别点的控件，我会尽力写下，然后继续放到这个库中，让大家一起学习。
### 我的自定义学习库，github地址：[辅助的自定义view系列](https://github.com/FuZhucheng/MyView)
#### 我会把自己写的学习的总结的自定义view更新给大家，一起学习！！喜欢的可以star或者fork下咯，也算对我的支持，哈哈，谢谢大家。
### 好了，Android-自定义View学习分享讲完了。本博客是博主这6个月学习安卓以来的自定义view部分，并在这里做出进一步拓展以及写出自己的理解。欢迎在下面指出错误，共同学习！
### 转载请注明：【JackFrost的博客】     
### 更多内容，可以访问[JackFrost的博客](http://blog.csdn.net/jack__frost?viewmode=contents)
