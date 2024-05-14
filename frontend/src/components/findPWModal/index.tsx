import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { updatePassword } from '@/api/userDetail/userAxios';
import * as S from '@/components/findPWModal/indexStyle';
import { AuthAtom } from '@/global/AuthAtom';

type Props = {
  isFindPWModal: boolean;
  onClose: () => void;
};

type PWData = {
  email: string;
  newPassword: string;
};

const FindPW: React.FC<Props> = ({ isFindPWModal, onClose }) => {
  const navigator = useNavigate();

  const [password, setPassword] = useState<string>('');
  const [passwordCheck, setPasswordCheck] = useState<boolean>(false);
  const [rePassword, setRePassword] = useState<string>('');
  const [checkPWFlag, setCheckPWFlag] = useState<boolean>(false);
  const [auth, setAuth] = useRecoilState(AuthAtom);

  useEffect(() => {
    if (password && rePassword) {
      setCheckPWFlag(true);
    } else {
      setCheckPWFlag(false);
    }
  }, [password, rePassword]);

  useEffect(() => {
    let vh = 0;
    vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`);
  }, [window.innerHeight]);

  const validatePassword = (password: string): boolean => {
    const password_regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    if (!password_regex.test(password)) {
      return false;
    } else {
      return true;
    }
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setPassword(e.target.value);
    setPasswordCheck(validatePassword(e.target.value));
  };

  const handlRePasswordChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setRePassword(e.target.value);
  };

  const handleCancle = (): void => {
    onClose();
  };

  const handleUpdatePW = (): void => {
    if (!checkPWFlag) {
      alert('모든 항목을 입력해주세요.');
      return;
    }

    const PWData: PWData = {
      email: auth.email,
      newPassword: password,
    };

    updatePassword<PWData>(
      auth.email,
      PWData,
      () => {
        alert('비밀번호 재설정완료');
        navigator('/login');
      },
      (err) => {
        console.log(err);
      },
    );
  };

  return (
    <S.FindLayout isFindPWModal={isFindPWModal}>
      <S.Container isFindPWModal={isFindPWModal}>
        <S.InputBox>
          <S.TitleText>비밀번호 재설정</S.TitleText>
          <S.Input
            type="password"
            placeholder="새 비밀번호"
            value={password}
            onChange={handlePasswordChange}
          />
          {passwordCheck ? (
            <S.Text>안전한 비밀번호 입니다.</S.Text>
          ) : (
            <S.WarnText>8~15자의 영문 대소문자, 숫자, 특수문자를 사용하세요.</S.WarnText>
          )}
          <S.Input
            type="password"
            placeholder="새 비밀번호 확인"
            value={rePassword}
            onChange={handlRePasswordChange}
          />
          {rePassword ? (
            password !== rePassword ? (
              <S.WarnText>비밀번호가 일치하지 않습니다.</S.WarnText>
            ) : (
              <S.Text>비밀번호가 일치합니다.</S.Text>
            )
          ) : (
            <S.EmptyText />
          )}
          <S.ButtonWrapper>
            <S.CancleButton onClick={handleCancle}>취소</S.CancleButton>
            <S.OKButton $checkPWFlag={checkPWFlag} onClick={handleUpdatePW}>
              확인
            </S.OKButton>
          </S.ButtonWrapper>
        </S.InputBox>
      </S.Container>
    </S.FindLayout>
  );
};

export default FindPW;
