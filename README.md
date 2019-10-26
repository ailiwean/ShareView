## ShareView
一个管理多个布局元素数据的库，用View模拟了Activity的栈结构，响应极其快速，布局之间通过订阅传递数据，让代码结构更加清晰。

##### 效果图
![image](https://github.com/ailiwean/ShareView/blob/master/img/demo.gif?raw=true)

#### 依赖

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


```
dependencies {
	        implementation 'com.github.ailiwean:ShareView:1.0.1'

```


# 用法
#### TaskDelegate栈效果


根Activity或Fragment只保留一个ShareView
```
<FrameLayout 
    android:layout_height="match_parent"
    tools:context=".MainActivity">
 
    <com.ailiwean.lib.ShareView
        android:id="@+id/mult"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</FrameLayout>

```
在ShareView中注册各个布局

```
     shareView.getTaskDelegate()
                .isLazyLoad(true) // 是否懒加载，切换到type后才会加载View
                .isReuseLayout(false)  //是否复用View， 不复用则页面的init只调用一次
                .setDefault(CONTENT)   //默认的展示View，在TaskDelegate下无效
                .regLayout(CONTENT, R.layout.aa) 
                .regLayout(INTOUNT,R.layout.bb)
                .bindCommonAnimation(anim)
                .go();
```
调用regLayout(int type, int res)注册后返回一个Build对象，可在这个Build中配置与该布局有关的操作，最后调用cp()返回ShareView。

```
                .init()
                .addLifeListener()
                .subscibe()
                .bindAnimation()
```
目前可配置这几项，init()会在View初始化时调用，addLifeListener() 添加View可视和隐藏的监听事件，subScribe()可订阅数据接收模型， bindAnimation()可单独配置该布局的入场出场动画。

**注意:**  

 - 栈效果也是遵循View的添加顺序原则，不然就要改动View层级关系，势必会带来性能消耗。
 - 栈效果下布局的复用与初始页是无效的也就是.isLazyLoad(true) .isReuseLayout(false)  两个方法无效。
 
#### 默认动画
没有动画的UI库则是一个残缺的库～手动滑稽～。可使用默认动画或者自定义动画快速的实现页面间切换效果

```
DefaultAnim anim = new DefaultAnim(300) {
            @Override
            public int taskTopEnter() {
                return AnimHelper.LEFT_ALL_SHOW;
            }

            @Override
            public int taskTopExit() {
                return AnimHelper.RIGHT_ALL_HIDE;
            }

            @Override
            public int taskInnerEnter() {
                return AnimHelper.RIGHT_HALF_SHOW;
            }

            @Override
            public int taskInnerExit() {
                return AnimHelper.LEFT_HALF_HIDE;
            }
        };
```

**说明：**

 - taskTopEnter ： 打开时新页的进入动画
 - taskTopExit： 返回时新页退出动画
 - taskInnerEnter ： 返回时老页的进入动画
 - taskInnerExit：打开时老页的退出动画

自带了几种效果，可自行尝试，后续继续扩展。

#### 自定义动画

```
 CustomAnim anim1 = new CustomAnim() {
            @Override
            protected void animEnter(View pageView, boolean isTopTask) {
                
            }

            @Override
            protected void animExit(View pageView, boolean isTopTask) {

            }
        };
```


pageView是要操作的View，isTopTask是区分那个页面。方法体内可自由发挥。

**注意：**

 - animEnter里动画开始前先调用finalEnterAnimStar() 传入回调的两个参数，结束后调用finalEnterAnimEnd()。
 - animExit里动画开始前先调用finalExitAnimStar(),结束后调用finalExitAnimEnd()。 
 
 这样库才能知道页面的动作最后作出相应的处理。

#### 设定动画
设定统一动画
```
bindCommonAnimation(anim)
```
为指定页设定单独动画

```
bindAnimation()
```
每个页默认是空动画
优先级：单独动画>用于动画>默认空动画
#### 数据发送接收
先在每个布局的配置里注册需要的订阅

```
 .subscibe(new TaskObserve<Integer>() {
                    @Override
                    public void response(TaskViewHolder vh, Integer integer) {
                        vh.setText(R.id.age, integer.toString());
                    }
                })
```
使用ShareView公共方法指定type发送

```
shareView.postData(CONTENT, vh.getText(R.id.name));
```

#### ViewHolder
为了方便操作不再使用头疼的引用所以增加了ViewHolder，持有了这个页面的rootView, 以便于快捷得到类型进行之后操作，扩展中...


#### MultiDelegate多状态效果
其实就是TaskDelegate去掉动画效果的阉割版， 不过用来写多个状态的布局还是非常好用。逻辑非常清晰。可以将多状态的公共操作单独出来，放成一个type, 然后注册的其他type做自己单独的操作。 布局共用一个，使用复用模式即可isReuseLayout(true)。 


##### 其他
感兴趣请留颗star，万分感谢🙏
好建议一定要联系我哦：
weixin：18209184113
qq:1581209979 
