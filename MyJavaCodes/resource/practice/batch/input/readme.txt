worker.txt
workername#단위시간당처리량#단위시간당소모cpu,단위시간당소모mem

batch.txt
batchname#처리량#시작시간#시작후납기#우선순위

policy.txt
due
request_order
priority

- batch 각각이 몇시에 시작해서 몇시에 마치는지 expect.log 에 출력
  b1#00:00:10,00:00:40
- 납기안에 끝나지 않을 것들을 delay.log 에 출력
  b1#종료지연예상시간
- 시간별 cpu/mem 사용율을 performance.log 에 출력
  00:00-00:01#25%#30%
- 콘솔로 특정 시간에 batch 우선순위를 변경할 경우
  expect_change1.log
  delay_change1.log
  performance_change1.log
- 소켓으로 특정 시간에 정책을 변경할 경우
  priority
  due
  request_order
  expect, delay, performance 를 socket 으로 회신
  