Ext.onReady(function() {
	//把动画时间减至5ms
	 Ext.dom.Element.override({
    		slideIn: function(anchor, obj, slideOut) {
    	        var me = this,
    	            elStyle = me.dom.style,
    	            beforeAnim,
    	            wrapAnim,
    	            restoreScroll,
    	            wrapDomParentNode;

    	        anchor = anchor || "t";
    	        obj = obj || {};

    	        beforeAnim = function() {
    	            var animScope = this,
    	                listeners = obj.listeners,
    	                box, originalStyles, anim, wrap;

    	            if (!slideOut) {
    	                me.fixDisplay();
    	            }

    	            box = me.getBox();
    	            if ((anchor == 't' || anchor == 'b') && box.height === 0) {
    	                box.height = me.dom.scrollHeight;
    	            }
    	            else if ((anchor == 'l' || anchor == 'r') && box.width === 0) {
    	                box.width = me.dom.scrollWidth;
    	            }

    	            originalStyles = me.getStyles('width', 'height', 'left', 'right', 'top', 'bottom', 'position', 'z-index', true);
    	            me.setSize(box.width, box.height);

    	            // Cache all descendants' scrollTop & scrollLeft values if configured to preserve scroll.
    	            if (obj.preserveScroll) {
    	                restoreScroll = me.cacheScrollValues();
    	            }

    	            wrap = me.wrap({
    	                id: Ext.id() + '-anim-wrap-for-' + me.id,
    	                style: {
    	                    visibility: slideOut ? 'visible' : 'hidden'
    	                }
    	            });
    	            wrapDomParentNode = wrap.dom.parentNode;
    	            wrap.setPositioning(me.getPositioning());
    	            if (wrap.isStyle('position', 'static')) {
    	                wrap.position('relative');
    	            }
    	            me.clearPositioning('auto');
    	            wrap.clip();

    	            // The wrap will have reset all descendant scrollTops. Restore them if we cached them.
    	            if (restoreScroll) {
    	                restoreScroll();
    	            }

    	            // This element is temporarily positioned absolute within its wrapper.
    	            // Restore to its default, CSS-inherited visibility setting.
    	            // We cannot explicitly poke visibility:visible into its style because that overrides the visibility of the wrap.
    	            me.setStyle({
    	                visibility: '',
    	                position: 'absolute'
    	            });
    	            if (slideOut) {
    	                wrap.setSize(box.width, box.height);
    	            }

    	            switch (anchor) {
    	                case 't':
    	                    anim = {
    	                        from: {
    	                            width: box.width + 'px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    elStyle.bottom = '0px';
    	                    break;
    	                case 'l':
    	                    anim = {
    	                        from: {
    	                            width: '0px',
    	                            height: box.height + 'px'
    	                        },
    	                        to: {
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    elStyle.right = '0px';
    	                    break;
    	                case 'r':
    	                    anim = {
    	                        from: {
    	                            x: box.x + box.width,
    	                            width: '0px',
    	                            height: box.height + 'px'
    	                        },
    	                        to: {
    	                            x: box.x,
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    break;
    	                case 'b':
    	                    anim = {
    	                        from: {
    	                            y: box.y + box.height,
    	                            width: box.width + 'px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            y: box.y,
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    break;
    	                case 'tl':
    	                    anim = {
    	                        from: {
    	                            x: box.x,
    	                            y: box.y,
    	                            width: '0px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    elStyle.bottom = '0px';
    	                    elStyle.right = '0px';
    	                    break;
    	                case 'bl':
    	                    anim = {
    	                        from: {
    	                            y: box.y + box.height,
    	                            width: '0px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            y: box.y,
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    elStyle.bottom = '0px';
    	                    break;
    	                case 'br':
    	                    anim = {
    	                        from: {
    	                            x: box.x + box.width,
    	                            y: box.y + box.height,
    	                            width: '0px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            x: box.x,
    	                            y: box.y,
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    break;
    	                case 'tr':
    	                    anim = {
    	                        from: {
    	                            x: box.x + box.width,
    	                            width: '0px',
    	                            height: '0px'
    	                        },
    	                        to: {
    	                            x: box.x,
    	                            width: box.width + 'px',
    	                            height: box.height + 'px'
    	                        }
    	                    };
    	                    elStyle.right = '0px';
    	                    break;
    	            }

    	            wrap.show();
    	            wrapAnim = Ext.apply({}, obj);
    	            delete wrapAnim.listeners;
    	            wrapAnim = new Ext.fx.Anim(Ext.applyIf(wrapAnim, {
    	                target: wrap,
    	                duration: 5,
    	                easing: 'ease-out',
    	                from: slideOut ? anim.to : anim.from,
    	                to: slideOut ? anim.from : anim.to
    	            }));

    	            // In the absence of a callback, this listener MUST be added first
    	            wrapAnim.on('afteranimate', function() {
    	                me.setStyle(originalStyles);
    	                if (slideOut) {
    	                    if (obj.useDisplay) {
    	                        me.setDisplayed(false);
    	                    } else {
    	                        me.hide();
    	                    }
    	                }
    	                if (wrap.dom) {
    	                    if (wrap.dom.parentNode) {
    	                        wrap.dom.parentNode.insertBefore(me.dom, wrap.dom);
    	                    } else {
    	                        wrapDomParentNode.appendChild(me.dom);
    	                    }
    	                    wrap.remove();
    	                }
    	                // The unwrap will have reset all descendant scrollTops. Restore them if we cached them.
    	                if (restoreScroll) {
    	                    restoreScroll();
    	                }
    	                // kill the no-op element animation created below
    	                animScope.end();
    	            });
    	            // Add configured listeners after
    	            if (listeners) {
    	                wrapAnim.on(listeners);
    	            }
    	        };

    	        me.animate({
    	            // See "A Note About Wrapped Animations" at the top of this class:
    	            duration: obj.duration ? Math.max(obj.duration, 500) * 2 : 1000,
    	            listeners: {
    	                beforeanimate: beforeAnim // kick off the wrap animation
    	            }
    	        });
    	        return me;
    	    }
        });
});