(ns chocolatier.engine.pixi)

(defn alter-obj!
  "Alter a js object's fields. Returns the object.
   
   Example: 
   (alter-obj! s  \"x\" 1 \"y\" 2)"
  [sprite & alterations]
  (doseq [[k v] (partition 2 alterations)]
    (aset sprite k v))
  sprite)

(defn add-child! [obj item]
  (.addChild obj item)
  obj)

(defn mk-graphic!
  "Initialize a new PIXI Graphics object into the stage"
  [stage]
  (let [graphic (new js/PIXI.Graphics)]
    (add-child! stage graphic)
    graphic))

(defn mk-stage []
  (new js/PIXI.Container))

(defn mk-renderer [width height]
  (new js/PIXI.CanvasRenderer width height nil true))

(defn mk-asset-loader [image-urls assets]
  (new js/PIXI.loaders.AssetLoader (apply array assets)))

(defn mk-texture [image-location]
  (js/PIXI.Texture.fromImage image-location))

(defn mk-sprite! [stage image-location]
  (let [texture (js/PIXI.Texture.fromImage image-location)
        sprite (js/PIXI.Sprite. texture)]
    (add-child! stage sprite)
    sprite))

(defn set-sprite-frame!
  "Set the frame of the sprite's spritesheet coords and dimensions
   Returns the updated sprite."
  [sprite x y w h]
  (let [texture (.-texture sprite)
        bounds (new js/PIXI.Rectangle x y w h)]
    (set! (.-frame texture) bounds))
  sprite)

(defn circle
  [graphic x y r]
  (.drawCircle graphic x y r)
  graphic)

(defn rectangle
  [graphic x y h w]
  (.beginFill graphic)
  (.drawRect graphic x y h w)
  (.endFill graphic)
  graphic)

(defn add-rect-mask!
  "Adds a mask to sprite at the screen location x, y with height and width h, w"
  [stage sprite x y h w]
  (-> (mk-graphic! stage)
      (rectangle x y h w)
      (#(alter-obj! sprite "mask" %))))

(defn mk-tiling-sprite [texture width height]
  (js/PIXI.extras.TilingSprite. texture width height))

(defn mk-display-object-container []
  (new js/PIXI.Container))

(defn mk-render-texture [w h]
  (new js/PIXI.RenderTexture w h))

(defn load-assets
  "Takes an asset loader from mk-asset-loader and loads assets using the 
   callback function f"
  [asset-loader f]
  (aset asset-loader "onComplete" f)
  (.load asset-loader))

(defn render!
  "Renders a pixi Stage object"
  [renderer stage]
  (.render renderer stage))

(defn line-style
  ([graphic weight]
   (.lineStyle graphic weight)
   graphic)
  ([graphic weight color]
   (.lineStyle graphic weight color)
   graphic))

(defn fill [graphic color-hex opacity]
  (.beginFill graphic color-hex opacity)
  graphic)

(defn clear [graphic]
  (.clear graphic)
  graphic)

(defn add-to-stage
  "Works for PIXI.Sprite and PIXI.Graphics and probably some others"
  [stage graphic]
  (add-child! stage graphic)
  graphic)

(defn render-from-object-container
  "Creates a texture from the sprites in container and renders them to the stage."
  [renderer stage container w h]
  (let [texture (.generateTexture container renderer)
        sprite (new js/PIXI.Sprite texture)]
    ;; This is a side-effect with no return value
    (add-child! stage sprite)))
