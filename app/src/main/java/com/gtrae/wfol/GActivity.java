package com.gtrae.wfol;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GActivity extends AppCompatActivity {
    ImageView im1;
    ImageView im2;
    ImageView im3;
    ImageView im4;
    ImageView im5;
    ImageView im6;
    ImageView im7;
    ImageView im8;
    ImageView temp1;
    ImageView temp2;
    ImageView nowClicked;
    int cardsOpen = 0;
    boolean animStopped = true;
    boolean restartClicked;
    int[] cards = {R.drawable.pc1, R.drawable.pc2, R.drawable.pc3, R.drawable.pc4, R.drawable.pc1, R.drawable.pc2, R.drawable.pc3, R.drawable.pc4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gactivity);
        im1 = findViewById(R.id.imageView1);
        im2 = findViewById(R.id.imageView2);
        im3 = findViewById(R.id.imageView3);
        im4 = findViewById(R.id.imageView4);
        im5 = findViewById(R.id.imageView5);
        im6 = findViewById(R.id.imageView6);
        im7 = findViewById(R.id.imageView7);
        im8 = findViewById(R.id.imageView8);
        cards = rand(cards);
    }

    public void onClick1(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[0]);
                    startFlipAnimation((ImageView) view, cards[0]);
                }
            }
        }
    }

    public void onClick2(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[1]);
                    startFlipAnimation((ImageView) view, cards[1]);
                }
            }
        }
    }

    public void onClick3(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[2]);
                    startFlipAnimation((ImageView) view, cards[2]);
                }
            }
        }
    }

    public void onClick4(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[3]);
                    startFlipAnimation((ImageView) view, cards[3]);
                }
            }
        }
    }

    public void onClick5(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[4]);
                    startFlipAnimation((ImageView) view, cards[4]);
                }
            }
        }
    }

    public void onClick6(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[5]);
                    startFlipAnimation((ImageView) view, cards[5]);
                }
            }
        }
    }

    public void onClick7(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[6]);
                    startFlipAnimation((ImageView) view, cards[6]);
                }
            }
        }
    }

    public void onClick8(View view) {
        if (view != nowClicked) {
            if (animStopped) {
                if (cardsOpen == 0) {
                    temp1 = (ImageView) view;
                }
                if (cardsOpen == 1) {
                    temp2 = (ImageView) view;
                }
                if (cardsOpen < 2) {
                    cardsOpen++;
                    view.setTag(cards[7]);
                    startFlipAnimation((ImageView) view, cards[7]);
                }
            }
        }
    }


    void startFlipAnimation(ImageView imageView, int resId) {
        animStopped = false;
        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flipping);
        anim.setTarget(imageView);
        anim.setDuration(500);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView.setImageResource(resId);
                endFlipAnimation(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        anim.start();
    }


    void endFlipAnimation(ImageView imageView) {
        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flipping_end);
        anim.setTarget(imageView);
        anim.setDuration(500);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                nowClicked = imageView;
                if (temp2 != null) {
                    if (temp1.getTag().equals(temp2.getTag())) {
                        temp1 = null;
                        temp2 = null;
                        cardsOpen = 0;
                        animStopped = true;
                    } else {
                        if (cardsOpen == 2) {
                            animStopped = false;
                            startCloseAnimation(temp1);
                            temp1 = null;
                            startCloseAnimation(temp2);
                            temp2 = null;
                            cardsOpen = 0;
                        }
                    }
                } else if (cardsOpen == 1)
                    animStopped = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        anim.start();
    }


    void startCloseAnimation(ImageView imageView) {
        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flipping_close_start);
        anim.setTarget(imageView);
        anim.setDuration(500);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView.setImageResource(R.drawable.closed);
                ObjectAnimator animEnd = (ObjectAnimator) AnimatorInflater.loadAnimator(getBaseContext(), R.animator.flipping_close_end);
                animEnd.setTarget(imageView);
                animEnd.setDuration(500);
                animEnd.start();
                animStopped = true;

                if (imageView == im8 && restartClicked) {
                    cardsOpen = 0;
                    temp1 = null;
                    temp2 = null;
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        anim.start();
    }


    static int[] rand(int array[]) {
        Random rd = new Random();
        int a = array.length - 1;
        for (int i = a - 1; i > 0; i--) {
            int j = rd.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    public void restartGame(View view) {
        startCloseAnimation(im1);
        startCloseAnimation(im2);
        startCloseAnimation(im3);
        startCloseAnimation(im4);
        startCloseAnimation(im5);
        startCloseAnimation(im6);
        startCloseAnimation(im7);
        startCloseAnimation(im8);
        restartClicked = true;
    }
}
