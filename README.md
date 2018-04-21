# AndroidLightFrameView

A normal View can have a light frame animation through wrapped by the LightFrameView.

LightFrameView only deal with first child view .

[]()

## Usage

### dependencies

```groovy
compile 'me.machao.lightframeview:lightframeview:1.0.0'
```

### XML layout code
```XML
<me.machao.lightframeview.LightFrameView
    android:id="@+id/lfvWrapView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:linearGradientEndColor="#f00"
    app:linearGradientStartColor="#ff0"
    app:strokeWidth="2dp"
    app:sweepGradientEndColor="#0f0"
    app:sweepGradientStartColor="#00f">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="LightFrameView wrap View" />
</me.machao.lightframeview.LightFrameView>
```

All we need to do is using `LightFrameView` wrap your normal `View`.

As we can see , `LightFrameView` support some xml attributes, you can check it here:
[attrs.xml](https://github.com/foolhorse/AndroidLightFrameView/blob/master/lightframeview/src/main/res/values/attrs.xml)

we can also change some behavior of `LightFrameView` through Java code :

```Java
lightFrameView.setEffectsEnable(!lightFrameView.isEffectsEnabled());
```

ok, let have some fun, make some vapor wave effects. lol.


[]()