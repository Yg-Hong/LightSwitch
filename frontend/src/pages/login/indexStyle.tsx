import styled from 'styled-components';

export const LogInLayout = styled.div`
  width: 100%;
  height: calc(var(--vh, 1vh) * 100 - 60px);
`;

export const LogInContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 2.5rem 3rem;
  gap: 1rem;
`;

export const LogInInput = styled.input`
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  border: 1px solid #ddd;
  font-size: 1.3rem;
  &::placeholder {
    color: #bdbdbd;
  }
`;

export const LogInText = styled.div`
  align-self: flex-end;
  font-size: 0.6rem;
  color: #00c9ea;
  font-weight: 500;
`;

export const LogInWarnText = styled.div`
  align-self: flex-end;
  font-size: 0.6rem;
  color: red;
  font-weight: 500;
`;

export const LogInInputBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 0.5rem;
`;

export const OKButton = styled.button`
  width: 100%;
  padding: 0.3rem 1rem;
  border-radius: 0.5rem;
  border: 1px solid #ddd;
  font-size: 1.4rem;
  background-color: #00c9ea;
  color: #fff;
  cursor: pointer;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 1rem;
`;