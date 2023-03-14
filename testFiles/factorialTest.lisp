(defun factorial (num)
   (cond
        ((> num 1) (* num (factorial (- num 1))))
        (T 1)
   )
)

(factorial 3)