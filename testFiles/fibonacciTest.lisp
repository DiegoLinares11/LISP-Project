(defun fibonacci (n)
    (cond
        ((< n 2) n)
        (T ( + (fibonacci (- n 1)) (fibonacci (- n 2))))
    )
)

(fibonacci 5)