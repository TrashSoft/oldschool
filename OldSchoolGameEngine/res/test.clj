;; Steps:
;;
;; INIT - Initialization of the program, e.g.: pre-loading of pictures/sprites
;; BUILD - Building up game model
;; MODIFY - Modification of the model, e.g. when something happens
;; CONTROL - Reaction on user interaction (event handler)
;; RENDER - Paint the current model on the canvas
;; DISPOSE - When the program is exitting
;; 

(def initStep (reify trash.oldschool.engine.GameEngineCallback
  (call [this facade]
      (.info (.logger facade) "Init Step Ran!")
      (.loadSprite (.spriteLibrary facade) "logo" "res/logo.jpg")
)))

(def disposeStep (reify trash.oldschool.engine.GameEngineCallback
  (call [this facade]
      (.info (.logger facade) "Dispose Step Ran!")
)))

(def modifyStep (reify trash.oldschool.engine.GameEngineCallback
  (call [this facade]
      ;; E.g.: moving the logo all around the screen based on the
      ;; time elapsed since the last call of this method
)))

(def renderStep (reify trash.oldschool.engine.GameEngineCallback
  (call [this facade]
      (.drawSprite (.graphics facade) "logo" 0 0)
)))

(.step

	(.step
	
		(.step
		
		  (.step
		
		    (.create (new trash.oldschool.engine.GameEngineBuilder)
			     "test-id"
			     "Test Program"
			     "v00.00.0000"
			     "This program only illustrates, how to use the engine with Clojure."
			   )
		
		    "INIT"
		    initStep
		  )
	
	    "MODIFY"
	    modifyStep
	  )

    "RENDER"
    renderStep
  )
  
  "DISPOSE"
  disposeStep
)
