
## ShareView
一个管理多个布局元素数据的库，用View模拟了Activity的栈结构，View的预加载，真正实现响应零延迟，布局之间通过订阅传递数据，代码结构更加清晰。

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
implementation 'com.github.ailiwean:ShareView:1.2.2'
```


# 用法
#### TaskDelegate栈效果
**创建一个ShareView**
```
<FrameLayout 
    android:layout_height="match_parent"
    tools:context=".MainActivity">
 
    <com.ailiwean.lib.ShareView
        android:id="@+id/shareView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</FrameLayout>

```
**创建栈结构对应Adapter**

```
 public class AAdapter extends TaskAdapter {
    public static int CONTENT = 1;

    @Override
    public int getLayoutId() {
        return R.layout.aa;
    }

    @Override
    public int getType() {
        return CONTENT;
    }

    @Override
    public void init(final TaskViewHolder vh) {
    }

    @Override
    public void lazy(TaskViewHolder vh) {
    }

    @Override
    public void preload(TaskViewHolder vh) {

    }

    @Override
    public int getFrontType() {
        return super.getFrontType();
    }
    
	@Override
    public boolean leaveRetain() {
        return false;
    }
}
```
 - getLayoutId()   :  提供一个LayoutID
 - getType()  : 提供该页的Key
 - init()  :   初始化时调用一次，当View销毁会重新调用
 - lazy()  :   延迟初始化(View完全展示)
 - preload()  :  预加载的实现执行子线程耗时等待操作时可预先加载			        其他页面的View
 - getFrontType()  :  标记前一个页面的Type，根页无需实现
 - leaveRetain()  :  该页退出后是否保留， false即销毁重建
 
数据类型订阅
 	在Adapter内部调用subscribe注册

```
 @Override
    public void init(final TaskViewHolder vh) {
        subscribe(new TaskObserve<String>() {
            @Override
            public void response(TaskViewHolder vh, String s) {
            }
        });
        subscribe(new TaskObserve<Integer>() {
            @Override
            public void response(TaskViewHolder vh, Integer integer) {
            }
        });
    }
   ```
发布事件
	

```
vh.getShareView().postData(CONTENT, "Hello Word");
vh.getShareView().postData(OTHER, 2);
```
为该页单独设定动画

```
	@Override
    public void init(final TaskViewHolder vh) {

        bindAnimation(new DefaultAnim(400) {
            @Override
            public int taskTopEnter() {
                return AnimHelper.ALPHA_UP_SHOW;
            }

            @Override
            public int taskTopExit() {
                return AnimHelper.ALPHA_DOWN_HIDE;
            }

            @Override
            public int taskInnerEnter() {
                return AnimHelper.RIGHT_HALF_SHOW;
            }

            @Override
            public int taskInnerExit() {
                return AnimHelper.LEFT_HALF_HIDE;
            }
        });

    }
```
 - taskTopEnter : 顶部页的进入动画
 - taskTopExit ：顶部页的退出动画
 - taskInnerEnter ：底部页的进入动画
 - taskInnerExit :  底部页的退出动画
 
 默认动画
 
```
	//无动画
    public static final int NULL = 0;
    //透明下退出
    public static final int ALPHA_DOWN_HIDE = 10000;
    //透明上进入
    public static final int ALPHA_UP_SHOW = 100001;
    //右退出
    public static final int RIGHT_ALL_HIDE = 100002;
    //左进入
    public static final int LEFT_ALL_SHOW = 100003;
    //右一半进入
    public static final int RIGHT_HALF_SHOW = 100004;
    //左一半退出
    public static final int LEFT_HALF_HIDE = 100005;
```
注册监听
  

```
@Override
    public void init(final TaskViewHolder vh) {
        addLifeListener(new LifeListener<TaskViewHolder>() {
            @Override
            public void onVisiable(TaskViewHolder vH) {
            }
            @Override
            public void onHide(TaskViewHolder vH) {
            }
        });
    }
```
 **绑定Adapter**
	  
```
 shareTask.getTaskDelegate()
                .regAdapter(new BAdapter())
                .cp()
                .regAdapter(new CAdapter())
                .cp()
                .regAdapter(new AAdapter())
                .cp()
                .go();
```
**Adapter对外开放**
	
```
 shareTask.getTaskDelegate()
                .regAdapter(new BAdapter())
                .subscibe(new TaskObserve<Object>() {
                })
                .addLifeListener(new LifeListener<TaskViewHolder>() {
                })
                .cp()
                .regAdapter(new CAdapter())
                .cp()
                .regAdapter(new AAdapter())
                .cp()
                .go();

```
Adapter会将构建好的Build返回，使用于依赖外部场景对象。

**启动页面**

传入对应Type
```
shareTask.goTo(OTHER);
 ```

**页面预加载**

调用一次回调一次preload()方法，View不存在时创建View
```
shareTask.preload(OTHER);
```
**回退栈**
按照如下处理， 返回true时表示未在栈底由内部消费。
```
 @Override
    public void onBackPressed() {
        if (shareTask.back())
            return;
        super.onBackPressed();
    }
```
**Api说明**

```
 .setDefault()            //设置默认type， 栈模式无效
 .isLazyLoad()				//是否懒加载，  false会创建所有View
 .isReuseLayout()        //是否复用View， 栈模式无效
 .bindCommonAnimation()        //绑定一个全局动画
```
#### 扩展
###### 自定义动画

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


pageView是要操作的View，isTopTask区分是否是否顶部。方法体内可自由发挥。

**注意：**

 - animEnter里动画开始前先调用finalEnterAnimStar() 传入回调的两个参数，结束后调用finalEnterAnimEnd()。
 - animExit里动画开始前先调用finalExitAnimStar(),结束后调用finalExitAnimEnd()。 
 

##### ViewHolder
为了方便操作不再使用头疼的引用所以增加了ViewHolder，持有各个View 提供快捷操作


##### MultiDelegate多状态效果
其实就是TaskDelegate去掉动画效果的阉割版， 不过用来写多个状态的布局还是非常好用。逻辑非常清晰。可以将多状态的公共操作单独出来，放成一个type, 然后注册的其他type做自己单独的操作。 布局共用一个，使用复用模式即可isReuseLayout(true)。 


License
-------

    Copyright 2019 ailiwean

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
