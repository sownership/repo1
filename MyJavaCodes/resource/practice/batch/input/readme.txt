worker.txt
workername#�����ð���ó����#�����ð���Ҹ�cpu,�����ð���Ҹ�mem

batch.txt
batchname#ó����#���۽ð�#�����ĳ���#�켱����

policy.txt
due
request_order
priority

- batch ������ ��ÿ� �����ؼ� ��ÿ� ��ġ���� expect.log �� ���
  b1#00:00:10,00:00:40
- ����ȿ� ������ ���� �͵��� delay.log �� ���
  b1#������������ð�
- �ð��� cpu/mem ������� performance.log �� ���
  00:00-00:01#25%#30%
- �ַܼ� Ư�� �ð��� batch �켱������ ������ ���
  expect_change1.log
  delay_change1.log
  performance_change1.log
- �������� Ư�� �ð��� ��å�� ������ ���
  priority
  due
  request_order
  expect, delay, performance �� socket ���� ȸ��
  