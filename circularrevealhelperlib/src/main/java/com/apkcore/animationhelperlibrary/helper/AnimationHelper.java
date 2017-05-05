package com.apkcore.animationhelperlibrary.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apkcore.animationhelperlibrary.helper.v1.ViewAnimationCompatUtils;

/**
 * Created by Apkcore on 2017/5/4.
 */

public class AnimationHelper {
    public static final int MINI_RADIUS = 0;
    public static final int DEFAULT_DURIATION = 500;

    /**
     * @param view
     * @param startRadius
     * @param durationMills
     */
    public static void show(final View view, float startRadius, long durationMills) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            view.setVisibility(View.VISIBLE);
//            return;
//        }
        int xCenter = (view.getLeft() + view.getRight()) / 2;
        int yCenter = (view.getTop() + view.getBottom()) / 2;
        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) (Math.sqrt(w * w + h * h) + 1);
        Animator animator = ViewAnimationCompatUtils.createCircularReveal(view, xCenter, yCenter, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animator.setDuration(durationMills);
        animator.start();
    }

    public static void hide(final View view, float endRadius, long durationMills, final int visible) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            view.setVisibility(visible);
//            return;
//        }
        int xCenter = (view.getLeft() + view.getRight()) / 2;
        int yCenter = (view.getTop() + view.getBottom()) / 2;
        int w = view.getWidth();
        int h = view.getHeight();

        int startRadius = (int) (Math.sqrt(w * w + h * h) + 1);
        Animator animator = ViewAnimationCompatUtils.createCircularReveal(view, xCenter, yCenter, startRadius, endRadius);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(visible);
            }
        });
        animator.setDuration(durationMills);
        animator.start();
    }

    public static void show(View myView) {
        show(myView, MINI_RADIUS, DEFAULT_DURIATION);
    }

    /**
     * 默认View隐藏状态为 GONE
     *
     * @param myView
     */
    public static void hide(View myView) {
        hide(myView, MINI_RADIUS, DEFAULT_DURIATION, View.GONE);
    }

    /**
     * @param myView     要隐藏的view
     * @param endVisible 动画执行结束是view的状态, 是View.INVISIBLE 还是GONE
     */
    public static void hide(View myView, int endVisible) {
        hide(myView, MINI_RADIUS, DEFAULT_DURIATION, endVisible);
    }

    public static void startActivityForResult(final BaseAnimationActivity thisActivity, final Intent intent,
                                              final Integer requestCode, final Bundle bundle,
                                              final View view, int colorOrImageRes, final long durationMills) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            if (requestCode == null) {
//                thisActivity.startActivity(intent);
//            } else if (bundle == null) {
//                thisActivity.startActivityForResult(intent, requestCode);
//            } else {
//                thisActivity.startActivityForResult(intent, requestCode, bundle);
//            }
//            return;
//        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        final int xCenter = location[0] + view.getWidth() / 2;
        final int yCenter = location[1] + view.getHeight() / 2;
        final ImageView imageview = new ImageView(thisActivity);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setImageResource(colorOrImageRes);

        final ViewGroup decorView = (ViewGroup) thisActivity.getWindow().getDecorView();
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        decorView.addView(imageview, w, h);

        int maxW = Math.max(xCenter, w - xCenter);
        int maxH = Math.max(yCenter, h - yCenter);
        final int finalRadius = (int) (Math.sqrt(maxW * maxW + maxH * maxH) + 1);
        Animator anim = ViewAnimationCompatUtils.createCircularReveal(imageview, xCenter, yCenter, 0, finalRadius);

        int maxRadius = (int) (Math.sqrt(w * w + h * h) + 1);
        long finalDuration = durationMills;
        if (finalDuration == DEFAULT_DURIATION) {
            double rate = 1d * finalRadius / maxRadius;//1d保留小数
            finalDuration = (long) (DEFAULT_DURIATION * rate);
        }
        anim.setDuration(finalDuration);
        anim.addListener(new AnimatorListenerAdapter() {
            @SuppressLint("NewApi")
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (requestCode == null) {
                    thisActivity.startActivity(intent);
                } else if (bundle == null) {
                    thisActivity.startActivityForResult(intent, requestCode);
                } else {
                    thisActivity.startActivityForResult(intent, requestCode, bundle);
                }

                thisActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    decorView.removeView(imageview);
//                    imageview.setVisibility(View.GONE);
//                    return;
//                }
                thisActivity.setResume(new BaseAnimationActivity.IonResume() {
                    @Override
                    public void setResume() {
                        if (imageview.getVisibility()==View.VISIBLE){
                            Animator anim = ViewAnimationCompatUtils.createCircularReveal(imageview, xCenter, yCenter, finalRadius, 0);
                            anim.setDuration(800);
                            anim.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    try {
                                        decorView.removeView(imageview);
                                        imageview.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            anim.start();
                        }
                    }
                });
//                view.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Animator anim = ViewAnimationCompatUtils.createCircularReveal(imageview, xCenter, yCenter, finalRadius, 0);
//                        anim.setDuration(durationMills);
//                        anim.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                try {
//                                    decorView.removeView(imageview);
//                                    imageview.setVisibility(View.GONE);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        anim.start();
//                    }
//                }, 1000);
            }
        });
        anim.start();
    }

    public static void startActivityForResult(
            BaseAnimationActivity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes) {
        startActivityForResult(thisActivity, intent, requestCode, null, triggerView, colorOrImageRes, DEFAULT_DURIATION);
    }

    public static void startActivity(
            BaseAnimationActivity thisActivity, Intent intent, View triggerView, int colorOrImageRes, long durationMills) {
        startActivityForResult(thisActivity, intent, null, null, triggerView, colorOrImageRes, durationMills);
    }

    public static void startActivity(
            BaseAnimationActivity thisActivity, Intent intent, View triggerView, int colorOrImageRes) {
        startActivity(thisActivity, intent, triggerView, colorOrImageRes, DEFAULT_DURIATION);
    }


    public static void startActivity(BaseAnimationActivity thisActivity, Class<?> targetClass, View triggerView, int colorOrImageRes) {
        startActivity(thisActivity, new Intent(thisActivity, targetClass), triggerView, colorOrImageRes, DEFAULT_DURIATION);
    }
}
