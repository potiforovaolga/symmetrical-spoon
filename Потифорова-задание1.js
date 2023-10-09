const [n1, n2, n3, n4, n5, n6] = [2, 3, 18, 4, -5, -8];
const m1 = n1*n5-n2*n4;
if (m1 != 0){
const m2 = n3*n5-n2*n6;
const x = m2/m1;
const m3 = n1*n6-n3*n4;

const y = m3/m1;
console.log('x равен ', x);
console.log('y равен ', y);
}


else {
console.log('Данное уравнение не возможно решить методом Крамера');
}