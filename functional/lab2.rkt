#lang racket
;;power-head number number -> number
(define (power-head a b)
  (cond
    [(= b 0) 1]
    [else (* a (power-head a (- b 1)))]))

;;power number number number -> number
(define (power a b n)
  (cond
    [(= b 0) n]
    [else (let ((m (- b 1)))
           (power a m (* n a)))]))

;;power-tail number number -> number
(define (power-tail a b)
  (power a b 1))

;;pruebas de Power
(power-head 4 3)
(power-tail 4 3)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;thirdi list number -> number
;; si lista es menor a 3 devuelve '()
(define (thirdi list a)
  (cond
    [(= a 1) (car list)]
    [else (let ((x (- a 1)))
            (cond
              [(empty? (cdr list)) (cons '(list is smaller than) (+ a 1))]
              [else (thirdi (cdr list) x)]))]))
 
;; cambio de nombre por <definition> de third
;; thirda list -> number
(define (thirda list)
  (thirdi list 3))

;;prueba third
(thirda (cons 1(cons 2 (cons 3 (cons 4 (cons 5 empty))))))
(thirda (cons 4 (cons 5 empty)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;list number -> bool
;;practicar con and
(define (just-twoi list x)
  (cond
    [(empty? (cdr list))
     (cond
       [(= x 2) #t]
       [else #f])]
    [else (just-twoi (cdr list) (+ x 1))]))

;; list -> bool
(define (just-two? list)
  (just-twoi list 1))

;;pruebas just-two?
(just-two? (cons 1 empty))
(just-two? (cons 1 (cons 4 empty)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; how-many-x? list number -> number
(define (how-many-x? list x)
  (how-many list x 0))

;; how-many list number number -> number
(define (how-many list x c)
  (cond
    [(empty? (cdr list)) (if (= x (car list)) (+ c 1) c)]
    [else (if (= x (car list))
      (how-many (cdr list) x (+ c 1))
      (how-many (cdr list) x c))]))

;; pruebas how many x?
(define list (cons 1(cons 2 (cons 3 (cons 4 (cons 3 empty))))))
(how-many-x? list 4)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; all-x? list number -> bool
(define (all-x? list x)
  (if (empty? (cdr list))
      (if (equal? x (car list)) #t #f)
      (if (equal? x (car list))
          (all-x? (cdr list) x)
          #f)))

;;pruebas all-x?
(define list1 (cons 3(cons 3 (cons 3 (cons 3 (cons 1 empty))))))
(define list2 (cons 3(cons 3 (cons 3 (cons 3 (cons 3 empty))))))
(all-x? list1 3)
(all-x? list2 3)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; get list number -> number
(define (get1 list x)
  (cond
    [(= x 1) (car list)]
    [else (get1 (cdr list) (- x 1))]))

;;pruebas get
(define list3 (cons 1(cons 2 (cons 3 (cons 4 (cons 3 empty))))))
(get1 list3 2)
(get1 list3 5)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; difference list list -> list
(define (difference A B)
  (diff A B '()))

;; diff list list list -> list
(define (diff A B res)
  (if (empty? (cdr A))
      (if (is-in-list B (car A)) res (cons (car A) res))
      (if (is-in-list B (car A))
                   (diff (cdr A) B res)
                   (diff (cdr A) B (cons (car A) res)))))

(define (addLast l x)
  (cond
    [(empty? l)(cons x empty)]
    [else (cons (car l)(addLast (cdr l) x))]))

(define (is-in-list list x)
  (cond
    [(= x (car list)) #t]
    [(empty? (cdr list)) #f]
    [else (is-in-list (cdr list) x)]))

(difference '(12 44 55 77 66 1 2 3 4) '(1 2 3))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; append list list -> list
(define (append A B)
  (if (empty? (cdr B))
      (addLast A (car B))
      (append (addLast A (car B)) (cdr B))))
        ;; [(empty? (cdr B)) A]
        ;; [else (append A (cdr B))])))

(append '(a b c d) '(e f g))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; invert list -> list
;; same as class
(define (invert l)
  (cond
    [(empty? l) '()]
    [else (addLast (invert (cdr l))(car l))]))

 (invert '(a b c d))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;sign list -> list
(define (sign lista)
  (signo lista '()))

(define (signo lista listab)
  (cond
    [(empty? lista) listab]
    [(< (car lista) 0) (signo (cdr lista) (addLast listab -1))]
    [(> (car lista) 0) (signo (cdr lista) (addLast listab 1))]))

;;prueba signo
(sign '(2 -4 -6))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; negatives list-> list
(define (negatives list)
  (negativos list '()))

(define (negativos lista listb)
  (cond
    [(empty? lista) listb]
    [(< (car lista) 0) (negativos (cdr lista) (addLast listb (car lista)))]
    [(>= (car lista) 0) (negativos (cdr lista) (addLast listb (* (car lista) -1)))]))
;; pruebas negatives

(negatives '(2 -4 6))



